package com.morni.mornimessagecenter.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
data class Meta(
    @SerializedName("has_more_items")
    val hasMoreItems: Boolean = false
)