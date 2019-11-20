package com.morni.mornimessagecenter.data.remote

import com.morni.mornimessagecenter.BuildConfig
import com.morni.mornimessagecenter.util.PrefsDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
object RetroClient {

    private var mApiService: ApiService? = null

    private fun createApiService(prefsDao: PrefsDao): ApiService {
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor(prefsDao))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(prefsDao.baseUrl!!)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

         return retrofit.create(ApiService::class.java)
    }

    /**
     * Singleton function to provide one instance of the api service through life of the application.
     */
    fun getApiService(prefsDao: PrefsDao) =
        mApiService ?: createApiService(prefsDao).apply { mApiService = this }
}