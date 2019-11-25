package com.morni.mornimessagecenter.util

import com.morni.mornimessagecenter.data.remote.ApiService

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
class Repository(
    var resourceProvider: ResourceProvider,
    var apiService: ApiService,
    val prefsDao: PrefsDao
)