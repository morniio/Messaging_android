package com.morni.mornimessagecenter.ui.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.data.remote.ApiService
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MessagesDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MorniMessage?>() {

    var responseLiveData: MutableLiveData<MorniApiStatus> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MorniMessage?>
    ) {
        compositeDisposable.add(
            apiService.getMessages(1)
                .doOnSubscribe { responseLiveData.postValue(MorniApiStatus.INITIAL_LOADING) }
                .subscribe(
                    { response ->
                        responseLiveData.postValue(MorniApiStatus.INITIAL_SUCCESS)
                        if (response.morniMessages.isNullOrEmpty()) {
                            responseLiveData.postValue(MorniApiStatus.EMPTY_DATA)
                        }
                        response?.morniMessages?.let { callback.onResult(it, null, 2) }

                    },
                    { error ->
                        if (error is HttpException) {
                            try {
                                if (error.response().code() == 401) {
                                    responseLiveData.postValue(MorniApiStatus.UN_AUTHORIZED)
                                } else {
                                    responseLiveData.postValue(MorniApiStatus.INITIAL_ERROR)
                                }
                            } catch (ex: IOException) {
                                responseLiveData.postValue(MorniApiStatus.INITIAL_ERROR)
                            }
                        } else {
                            responseLiveData.postValue(MorniApiStatus.INITIAL_NO_INTERNET)
                        }
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MorniMessage?>) {
        compositeDisposable.add(
            apiService.getMessages(params.key)
                .doOnSubscribe { responseLiveData.postValue(MorniApiStatus.LOADING) }
                .subscribe(
                    { response ->
                        responseLiveData.postValue(MorniApiStatus.SUCCESS)
                        if (response.meta?.hasMoreItems == true) {
                            response?.morniMessages?.let { callback.onResult(it, params.key + 1) }
                        }
                    },
                    { error ->
                        if (error is HttpException) {
                            try {
                                if (error.response().code() == 401) {
                                    responseLiveData.postValue(MorniApiStatus.UN_AUTHORIZED)
                                } else {
                                    responseLiveData.postValue(MorniApiStatus.ERROR)
                                }
                            } catch (ex: IOException) {
                                responseLiveData.postValue(MorniApiStatus.ERROR)
                            }
                        } else {
                            responseLiveData.postValue(MorniApiStatus.NO_INTERNET)
                        }
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MorniMessage?>) = Unit

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}