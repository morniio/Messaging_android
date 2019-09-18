package com.morni.mornimessagecenter.data.remote

import com.morni.mornimessagecenter.util.PrefsDao
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Rami El-bouhi on 09,September,2019
 */

private const val AUTHORIZATION = "Authorization"
const val BEARER = "Token"
private const val ACCEPT_LANGUAGE = "Accept-Language"
private const val ACCEPT_ENCODING = "Accept-Encoding"
private const val ACCEPT = "Accept"
private const val PLATFORM = "Platform"
private const val CONTENT_TYPE = "Content-Type"
private const val APP_VERSION = "App-Version"
private const val CONTENT_TYPE_VALUE = "application/json"
private const val ANDROID = "android"

class AuthInterceptor constructor(private val prefsDao: PrefsDao) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        addHeaders(requestBuilder, prefsDao)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun addHeaders(requestBuilder: Request.Builder, prefsDao: PrefsDao) {
        if (!prefsDao.accessToken.isNullOrBlank()) {
            requestBuilder.addHeader(AUTHORIZATION, "$BEARER ${prefsDao.accessToken}")
        }
        requestBuilder.addHeader(ACCEPT_LANGUAGE, prefsDao.language)
        requestBuilder.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
        requestBuilder.addHeader(ACCEPT_ENCODING, CONTENT_TYPE_VALUE)
        requestBuilder.addHeader(ACCEPT, CONTENT_TYPE_VALUE)
        requestBuilder.addHeader(PLATFORM, ANDROID)
        requestBuilder.addHeader(APP_VERSION, "2.2.2")
    }
}