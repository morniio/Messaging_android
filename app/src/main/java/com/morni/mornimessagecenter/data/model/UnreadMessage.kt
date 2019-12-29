package com.morni.mornimessagecenter.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Abdulmajeed Alyafey on 2019-29-12.
 */
data class UnreadMessage(
    @SerializedName("total_unread_messages")
    var totalUnread: Int? = 0
)