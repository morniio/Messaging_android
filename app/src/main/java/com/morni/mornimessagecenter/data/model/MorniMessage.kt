package com.morni.mornimessagecenter.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
data class MorniMessage(
    @SerializedName("id")
    var id: Long? = 0,

    @SerializedName("title")
    var title: String? = "",

    @SerializedName("subtitle")
    var subTitle: String? = "",

    @SerializedName("body")
    var body: String? = null ?: "",

    @SerializedName("created_at")
    var createdAt: String? = "",

    @SerializedName("read")
    var isRead: Boolean? = false
)
