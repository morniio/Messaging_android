package com.morni.mornimessagecenter.data

import com.morni.mornimessagecenter.data.local.PrefsDao
import com.morni.mornimessagecenter.data.remote.ApiService
import com.morni.mornimessagecenter.util.ResourceProvider

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
class Repository(
    var resourceProvider: ResourceProvider,
    var apiService: ApiService,
    val prefsDao: PrefsDao
)