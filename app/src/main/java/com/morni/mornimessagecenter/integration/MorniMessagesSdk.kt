package com.morni.mornimessagecenter.integration

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.MessagesServiceProvider
import com.morni.mornimessagecenter.di.Injection
import com.morni.mornimessagecenter.ui.activity.MorniMessageActivity
import com.morni.mornimessagecenter.util.LocaleHelper
import com.morni.mornimessagecenter.util.showAlertDialog
import okhttp3.Interceptor

/**
 * Created by Rami El-bouhi on 17,September,2019
 */
class MorniMessagesSdk(private val activity: Activity) {

    private val prefsDao by lazy { Injection.providePreference(activity) }

    private var defaultActivity: Class<*>? = null
    private val moreExtras = HashMap<String, Any>()
    private var requestCode: Int = REQUEST_CODE
    private var httpHeader: Interceptor? = null
    private var baseUrl: String? = null
    private var accessToken: String? = null
    private var language: String? = null
    private var pageSize: Int? = null
    private var messageId: Long? = null

    /**
     * Check entered attributes if all mandatory fields are entered.
     * returns null if validation fails, otherwise completes initiating preference.
     */
    fun initiate(): MorniMessagesSdk? = apply {
        val errorsList = ArrayList<String>()

        if (httpHeader == null)
            errorsList.add("${activity.getString(R.string.http_header)} ${activity.getString(R.string.is_missing)}")

        if (baseUrl.isNullOrBlank())
            errorsList.add("${activity.getString(R.string.base_url)} ${activity.getString(R.string.is_missing)}")

        if (accessToken.isNullOrBlank())
            errorsList.add("${activity.getString(R.string.access_token)} ${activity.getString(R.string.is_missing)}")

        if (errorsList.isNotEmpty()) {
            showAlertDialog(
                activity,
                activity.getString(R.string.missing_data),
                errorsList.joinToString("\n"),
                DialogInterface.OnClickListener { dialogInterface: DialogInterface?, _: Int ->
                    dialogInterface?.dismiss()
                }
            )
            return null
        } else {
            prefsDao.apply {
                httpHeader = this@MorniMessagesSdk.httpHeader
                baseUrl = this@MorniMessagesSdk.baseUrl
                accessToken = this@MorniMessagesSdk.accessToken
                language = this@MorniMessagesSdk.language ?: LocaleHelper.DEFAULT_LANGUAGE
                pageSize = this@MorniMessagesSdk.pageSize ?: Intents.DEFAULT_PAGE_SIZE
                messageId = this@MorniMessagesSdk.messageId
            }
        }
    }

    fun showMessages() = activity.startActivityForResult(createIntent(), requestCode)

    fun getTotalUnreadMessages(func: (Int?) -> Unit) =
        MessagesServiceProvider.getTotalUnreadMessages(
            Injection.provideRepository(activity),
            func
        )

    fun setHttpHeader(header: Interceptor?) = apply {
        if (header != null) {
            httpHeader = header
            addExtra(Intents.HTTPS_HEADER, header)
        }
    }

    fun setBaseUrl(url: String) = apply {
        if (url.isNotEmpty()) {
            baseUrl = url
            addExtra(Intents.BASE_URL, url)
        }
    }

    fun setAccessToken(token: String) = apply {
        if (token.isNotEmpty()) {
            accessToken = token
            addExtra(Intents.ACCESS_TOKEN, token)
        }
    }

    fun setLanguage(lang: String) = apply {
        if (lang.isNotEmpty()) {
            language = lang
            addExtra(Intents.LANGUAGE, lang)
        }
    }

    fun setPageSize(_pageSize: Int) = apply {
        if (_pageSize > 0) {
            pageSize = _pageSize
            addExtra(Intents.PAGE_SIZE, _pageSize)
        }
    }

    /**
     * This will be set if user wants to open details of the message by its id directly.
     */
    fun setMessageId(_messageId: Long) = apply {
        if (_messageId > 0) {
            messageId = _messageId
            addExtra(Intents.MESSAGE_ID, _messageId)
        }
    }

    private fun addExtra(key: String, value: Any) = apply { moreExtras[key] = value }

    private fun createIntent() = Intent(activity, getDefaultActivity()).apply {
        action = Intents.START_ACTION
        for ((key, value) in moreExtras) { // Kind of hacky
            when (value) {
                is Int -> putExtra(key, value)
                is Long -> putExtra(key, value)
                is Boolean -> putExtra(key, value)
                is Double -> putExtra(key, value)
                is Float -> putExtra(key, value)
                is Bundle -> putExtra(key, value)
                is IntArray -> putExtra(key, value)
                is LongArray -> putExtra(key, value)
                is BooleanArray -> putExtra(key, value)
                is DoubleArray -> putExtra(key, value)
                is FloatArray -> putExtra(key, value)
                else -> putExtra(key, value.toString())
            }
        }
    }

    private fun getDefaultActivity(): Class<*> {
        if (defaultActivity == null) defaultActivity = MorniMessageActivity::class.java
        return defaultActivity as Class<*>
    }

    companion object {
        const val REQUEST_CODE: Int = 0x0000c0de

        @JvmStatic
        fun parseActivityResult(
            requestCode: Int,
            resultCode: Int,
            intent: Intent?
        ) = if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val status = intent?.getIntExtra(Intents.STATUS, 0)
            val message = intent?.getStringExtra(Intents.MESSAGE)
            IntentResult(status, message)
        } else null
    }
}