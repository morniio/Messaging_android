package com.morni.mornimessagecenter.data.model

import com.squareup.moshi.Json

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MessagesResponse {
    var status: String? = null
    var message: String? = null
    var meta: Meta? = null
    @Json(name = "data")
    var morniMessages: List<MorniMessage>? = null
}