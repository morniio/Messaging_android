package com.morni.mornimessagecenter.data.model

import com.squareup.moshi.Json

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MorniMessage{
    @Json(name = "id") var id: Long? = 0
    var title: String? = ""
    @Json(name = "subtitle") var subTitle: String? = ""
    var body: String? = null ?: ""
    @Json(name = "created_at") var createdAt: String? = ""
    @Json(name = "read") var isRead: Boolean? = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        return true
    }
}