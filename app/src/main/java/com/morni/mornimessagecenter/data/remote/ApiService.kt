package com.morni.mornimessagecenter.data.remote

import com.morni.mornimessagecenter.data.model.MessageDetailsResponse
import com.morni.mornimessagecenter.data.model.MessagesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
interface ApiService {


    @GET("management_messages")
    fun getMessages(@Query("page") page: Int): Single<MessagesResponse>

    @GET("management_messages/{id}")
    fun getMessageDetails(@Path(value = "id") id: Long): Single<MessageDetailsResponse>

}