package com.morni.mornimessagecenter.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rami El-bouhi on 17,September,2019
 */
class MessageDetailsResponse {

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: MorniMessage?

    @Expose
    val morniApiStatus: MorniApiStatus

    constructor(morniApiStatus: MorniApiStatus, message: String?, morniMessage: MorniMessage?) {
        this.morniApiStatus = morniApiStatus
        this.message = message
        this.data = morniMessage
    }

    companion object {
        fun loading(): MessageDetailsResponse {
            return MessageDetailsResponse(MorniApiStatus.LOADING, null, null)
        }

        fun success(morniMessage: MorniMessage?): MessageDetailsResponse {
            return MessageDetailsResponse(MorniApiStatus.SUCCESS, null, morniMessage)
        }

        fun error(message: String?): MessageDetailsResponse {
            return MessageDetailsResponse(MorniApiStatus.ERROR, message, null)
        }

        fun authorizationError(message: String?): MessageDetailsResponse {
            return MessageDetailsResponse(MorniApiStatus.UN_AUTHORIZED, message, null)
        }
    }
}