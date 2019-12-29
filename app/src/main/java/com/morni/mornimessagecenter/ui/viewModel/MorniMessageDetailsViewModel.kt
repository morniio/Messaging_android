package com.morni.mornimessagecenter.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MessageDetailsResponse
import com.morni.mornimessagecenter.data.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

class MorniMessageDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val messageDetailsResponse: MutableLiveData<MessageDetailsResponse> = MutableLiveData()

    fun getMessageDetails(id: Long) {
        compositeDisposable.add(repository.apiService.getMessageDetails(id)
            .doOnSubscribe { messageDetailsResponse.postValue(MessageDetailsResponse.loading()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    messageDetailsResponse.postValue(MessageDetailsResponse.success(response.data))
                },
                { error ->
                    if (error is HttpException) {
                        val body: ResponseBody? = error.response().errorBody()
                        val adapter: TypeAdapter<MessageDetailsResponse> =
                            Gson().getAdapter<MessageDetailsResponse>(MessageDetailsResponse::class.java)
                        try {
                            val errorParser: MessageDetailsResponse =
                                adapter.fromJson(body!!.string())
                            if (error.code() == 401) {
                                messageDetailsResponse.postValue(
                                    MessageDetailsResponse.authorizationError(
                                        errorParser.message
                                    )
                                )
                            } else {
                                messageDetailsResponse.postValue(
                                    MessageDetailsResponse.error(
                                        errorParser.message
                                    )
                                )
                            }
                        } catch (ex: IOException) {
                            messageDetailsResponse.postValue(
                                MessageDetailsResponse.error(
                                    repository.resourceProvider.getString(
                                        R.string.error_msg
                                    )
                                )
                            )
                        }
                    } else {
                        messageDetailsResponse.postValue(
                            MessageDetailsResponse.error(
                                repository.resourceProvider.getString(R.string.no_internet_connection)
                            )
                        )
                    }
                }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
