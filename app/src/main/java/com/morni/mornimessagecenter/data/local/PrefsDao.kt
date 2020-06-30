package com.morni.mornimessagecenter.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.morni.mornimessagecenter.integration.Intents
import com.morni.mornimessagecenter.util.LocaleHelper
import com.morni.mornimessagecenter.util.extentions.get
import com.morni.mornimessagecenter.util.extentions.set
import okhttp3.Interceptor


/**
 * Created by Rami El-bouhi on 09,September,2019
 */
class PrefsDao constructor(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var httpHeader: Interceptor?
        get() {
            val interceptorAsGson =  sharedPreferences.getString(HTTP_HEADER, null)
            val builder = GsonBuilder()
            builder.registerTypeAdapter(Interceptor::class.java, InterfaceAdapter())
            val gson = builder.create()
            return Gson().fromJson(interceptorAsGson, Interceptor::class.java)
        }
        set(value) {
            sharedPreferences[HTTP_HEADER]= Gson().toJson(value)
        }

    var accessToken: String?
        get() = sharedPreferences[ACCESS_TOKEN]
        set(value) {
            sharedPreferences[ACCESS_TOKEN] = value
        }

    var baseUrl: String?
        get() = sharedPreferences[BASE_URL]
        set(value) {
            sharedPreferences[BASE_URL] = value
        }

    var language: String?
        get() = sharedPreferences[LANGUAGE, LocaleHelper.DEFAULT_LANGUAGE]
        set(value) {
            sharedPreferences[LANGUAGE] = value
        }

    var appVersion: String?
        get() = sharedPreferences[APP_VERSION]
        set(value) {
            sharedPreferences[APP_VERSION] = value
        }

    var pageSize: Int?
        get() = sharedPreferences[PAGE_SIZE, Intents.DEFAULT_PAGE_SIZE]
        set(value) {
            sharedPreferences[PAGE_SIZE] = value
        }

    var messageId: Long?
        get() = sharedPreferences[MESSAGE_ID]
        set(value) {
            sharedPreferences[MESSAGE_ID] = value
        }

    fun clear() = sharedPreferences.edit().clear().apply()

    companion object {
        private var mInstance: PrefsDao? = null

        private const val PREFS_NAME = "rememberMe"
        private const val ACCESS_TOKEN = "token"
        private const val BASE_URL = "base_url"
        private const val LANGUAGE = "language"
        private const val APP_VERSION = "app_version"
        private const val PAGE_SIZE = "page_size"
        private const val MESSAGE_ID = "message_id"
        private const val HTTP_HEADER = "http_header"

        fun getInstance(context: Context): PrefsDao
                = mInstance
            ?: PrefsDao(context).apply { mInstance = this }
    }
}