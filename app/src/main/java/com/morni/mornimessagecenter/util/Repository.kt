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

    companion object {
        private var mInstance: Repository? = null

        fun getInstance(
            resourceProvider: ResourceProvider,
            apiService: ApiService,
            prefsDao: PrefsDao
        )= mInstance ?: Repository(resourceProvider, apiService, prefsDao)
            .apply { mInstance = this }
    }
}