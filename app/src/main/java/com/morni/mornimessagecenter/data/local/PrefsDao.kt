package com.morni.mornimessagecenter.data.local

import android.content.Context
import android.content.SharedPreferences
import com.morni.mornimessagecenter.integration.Intents
import com.morni.mornimessagecenter.util.LocaleHelper
import com.morni.mornimessagecenter.util.extentions.get
import com.morni.mornimessagecenter.util.extentions.set

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
class PrefsDao constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

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
    var store: String?
        get() = sharedPreferences[STORE, Intents.DEFAULT_STORE]
        set(value) {
            sharedPreferences[STORE] = value
        }
    var appType: String?
        get() = sharedPreferences[APP_TYPE, ""]
        set(value) {
            sharedPreferences[APP_TYPE] = value
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
        private const val STORE = "store"
        private const val APP_TYPE = "app_type"

        fun getInstance(context: Context) = mInstance
            ?: PrefsDao(context).apply { mInstance = this }
    }
}