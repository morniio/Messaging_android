package com.morni.mornimessagecenter.ui.datasource.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.data.remote.ApiService
import com.morni.mornimessagecenter.ui.datasource.MessagesDataSource
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MessagesDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val apiService: ApiService
) : DataSource.Factory<Int, MorniMessage>() {

    //lateinit var messagesDataSource: MessagesDataSource
    val messagesDataSourceLiveData = MutableLiveData<MessagesDataSource>()

    override fun create(): DataSource<Int, MorniMessage?> {
        val messagesDataSource = MessagesDataSource(apiService, compositeDisposable)
        messagesDataSourceLiveData.postValue(messagesDataSource)
        return messagesDataSource
    }
}