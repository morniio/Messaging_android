package com.morni.mornimessagecenter.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Abdulmajeed Alyafey on 2019-29-12.
 */
data class UnreadMessagesResponse(
    @SerializedName("status")
    var status: String? = null,

    @SerializedName("message")
    var alertMessage: String? = null,

    @SerializedName("data")
    var data: UnreadMessage? = null
)