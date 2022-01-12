package com.morni.mornimessagecenter.data.remote

import com.morni.mornimessagecenter.BuildConfig
import com.morni.mornimessagecenter.data.local.PrefsDao
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
object RetroClient {

    fun getApiService(prefsDao: PrefsDao): ApiService {
        val authInterceptor : Interceptor =
            if (prefsDao.httpHeader != null) prefsDao.httpHeader!! else DefaultAuthInterceptor(prefsDao)

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
            })
            .build()
        val retrofit = Retrofit
            .Builder()
            .baseUrl(prefsDao.baseUrl ?: "")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}