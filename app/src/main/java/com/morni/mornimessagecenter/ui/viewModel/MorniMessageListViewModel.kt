package com.morni.mornimessagecenter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.morni.mornimessagecenter.data.Repository
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.integration.Intents
import com.morni.mornimessagecenter.ui.datasource.MessagesDataSource
import com.morni.mornimessagecenter.ui.datasource.factory.MessagesDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MorniMessageListViewModel(repository: Repository) : ViewModel() {

    private val statusResponse: LiveData<MorniApiStatus>
    private var morniMessages: LiveData<PagedList<MorniMessage>>
    private val compositeDisposable = CompositeDisposable()
    private val messagesDataSourceFactory: MessagesDataSourceFactory
    private val executor: Executor

    init {
        executor = Executors.newFixedThreadPool(5)
        messagesDataSourceFactory =
            MessagesDataSourceFactory(compositeDisposable, repository.apiService)
        val config = PagedList.Config.Builder()
            .setPageSize(repository.prefsDao.pageSize ?: Intents.DEFAULT_PAGE_SIZE)
            .setInitialLoadSizeHint(repository.prefsDao.pageSize ?: Intents.DEFAULT_PAGE_SIZE * 2)
            .setEnablePlaceholders(true)
            .build()
        morniMessages = LivePagedListBuilder(messagesDataSourceFactory, config)
            .setFetchExecutor(executor).build()
        statusResponse =
            Transformations.switchMap<MessagesDataSource, MorniApiStatus>(
                messagesDataSourceFactory.messagesDataSourceLiveData,
                MessagesDataSource::responseLiveData
            )
    }

    fun statusResponse(): LiveData<MorniApiStatus> = statusResponse

    fun messagesResponse(): LiveData<PagedList<MorniMessage>> = morniMessages

    fun retry() {
        messagesDataSourceFactory.messagesDataSourceLiveData.value?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun refresh() {
        messagesDataSourceFactory.messagesDataSourceLiveData.value?.invalidate()
    }
}