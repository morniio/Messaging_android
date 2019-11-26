package com.morni.mornimessagecenter.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
data class MessagesResponse(
    @SerializedName("status")
    var status: String? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("meta")
    var meta: Meta? = null,

    @SerializedName("data")
    var morniMessages: List<MorniMessage>? = null
)