package com.morni.mornimessagecenter.util

import com.morni.mornimessagecenter.data.model.MessageDetailsResponse
import com.morni.mornimessagecenter.data.remote.ApiService
import io.reactivex.Single

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
class Repository(
    var resourceProvider: ResourceProvider,
    var apiService: ApiService,
    val prefsDao: PrefsDao
) {

    fun getMessageDetails(id: Long): Single<MessageDetailsResponse> {
        return apiService.getMessageDetails(id)
    }
}