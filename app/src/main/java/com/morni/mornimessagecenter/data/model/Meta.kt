package com.morni.mornimessagecenter.data.model

import com.squareup.moshi.Json

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
data class Meta(@Json(name = "has_more_items") val hasMoreItems: Boolean = false)