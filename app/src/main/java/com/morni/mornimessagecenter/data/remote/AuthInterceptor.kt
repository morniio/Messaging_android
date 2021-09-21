package com.morni.mornimessagecenter.data.remote

import android.os.Build
import com.morni.mornimessagecenter.data.local.PrefsDao
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

/**
 * Created by Rami El-bouhi on 18,September,2021
 */
class AuthInterceptor(private val prefHelper: PrefsDao) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val request = with(builder) { addHeaders(this, prefHelper) }.build()
        return chain.proceed(request)
    }

    private fun addHeaders(
        builder: Request.Builder,
        prefHelper: PrefsDao
    ) = builder.apply {
        addHeader(HeaderMetaData.AUTHORIZATION, HeaderMetaData.TOKEN + prefHelper.accessToken)
        addHeader(HeaderMetaData.CONTENT_TYPE, HeaderMetaData.APPLICATION_JSON)
        addHeader(HeaderMetaData.ACCEPT, HeaderMetaData.APPLICATION_JSON)
        addHeader(HeaderMetaData.ACCEPT_LANGUAGE, Locale.getDefault().language)
        addHeader(HeaderMetaData.PLATFORM, HeaderMetaData.ANDROID)
        addHeader(HeaderMetaData.APP_SOURCE, prefHelper.store ?: HeaderMetaData.GOOGLE_STORE)
        addHeader(HeaderMetaData.APP_VERSION, prefHelper.appVersion ?: "")
        addHeader(HeaderMetaData.OS_VERSION, Build.VERSION.RELEASE)
        addHeader(HeaderMetaData.MODEL, Build.MODEL)
        addHeader(HeaderMetaData.APP_TYPE, prefHelper.appType ?: "")
    }


    object HeaderMetaData {
        // Keys.
        const val AUTHORIZATION = "Authorization"
        const val CONTENT_TYPE = "Content-Type"
        const val ACCEPT_LANGUAGE = "Accept-Language"
        const val PLATFORM = "Platform"
        const val APP_VERSION = "App-Version"
        const val OS_VERSION = "OS-Version"
        const val MODEL = "Model"
        const val ACCEPT = "Accept"
        const val APP_SOURCE = "App-Source"
        const val APP_TYPE = "App-Type"

        //Static Values.
        const val TOKEN = "Token "
        const val APPLICATION_JSON = "Application/json"
        const val ANDROID = "Android"
        const val GOOGLE_STORE = "google_store"
    }
}