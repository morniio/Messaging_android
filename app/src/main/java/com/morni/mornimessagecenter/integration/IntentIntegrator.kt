package com.morni.mornimessagecenter.integration

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.di.Injection
import com.morni.mornimessagecenter.ui.activity.MorniMessageActivity
import com.morni.mornimessagecenter.util.LocaleHelper
import com.morni.mornimessagecenter.util.showAlertDialog

/**
 * Created by Rami El-bouhi on 17,September,2019
 */
class IntentIntegrator(private val activity: Activity) {

    private val prefsDao by lazy { Injection.providePreference(activity) }

    private var defaultActivity: Class<*>? = null
    private val moreExtras = HashMap<String, Any>()
    private var fragment: Fragment? = null
    private var requestCode: Int = REQUEST_CODE

    private var baseUrl: String? = null
    private var accessToken: String? = null
    private var appVersion: String? = null
    private var language: String? = null
    private var pageSize: Int? = null
    private var messageId: Long? = null


    companion object {
        const val REQUEST_CODE: Int = 0x0000c0de

        /**
         *
         * Call this from your [Activity]'s
         * [Activity.onActivityResult] method.
         *
         *
         * This checks that the requestCode is equal to the default REQUEST_CODE.
         *
         * @param requestCode request code from `onActivityResult()`
         * @param resultCode  result code from `onActivityResult()`
         * @param intent      [Intent] from `onActivityResult()`
         * @return null if the event handled here was not related to this class, or
         * else an [IntentResult] containing the result of the scan. If the user cancelled scanning,
         * the fields will be null.
         */
        fun parseActivityResult(requestCode: Int, resultCode: Int, intent: Intent?): IntentResult? {
            return if (requestCode == REQUEST_CODE) {
                parseActivityResult(resultCode, intent)
            } else null
        }

        /**
         * Parse activity result, without checking the request code.
         *
         * @param resultCode result code from `onActivityResult()`
         * @param intent     [Intent] from `onActivityResult()`
         * @return an [IntentResult] containing the result of the scan. If the user cancelled scanning,
         * the fields will be null.
         */
        fun parseActivityResult(resultCode: Int, intent: Intent?): IntentResult {
            if (resultCode == Activity.RESULT_OK) {
                val status = intent?.getIntExtra(Intents.STATUS, 0)
                val message = intent?.getStringExtra(Intents.MESSAGE)
                return IntentResult(status, message)
            }
            return IntentResult(0, null)
        }
    }

    fun initiate() {
        // check entered attributes if all mandatory fields are entered
        val errorsList = ArrayList<String>()
        if (baseUrl.isNullOrBlank()) {
            errorsList.add("${activity.getString(R.string.base_url)} ${activity.getString(R.string.is_missing)}")
        }
        if (accessToken.isNullOrBlank()) {
            errorsList.add("${activity.getString(R.string.access_token)} ${activity.getString(R.string.is_missing)}")
        }
        if (appVersion.isNullOrBlank()) {
            errorsList.add(
                "${activity.getString(R.string.application_version)} ${activity.getString(
                    R.string.is_missing
                )}"
            )
        }
        if (errorsList.size > 0) {
            val errorMsg = errorsList.joinToString("\n")
            showAlertDialog(
                activity,
                activity.getString(R.string.missing_data),
                errorMsg
                ,
                DialogInterface.OnClickListener { dialogInterface: DialogInterface?, _: Int -> dialogInterface?.dismiss() }
            )
        } else {
            // Initilize preference and open main activity.
            prefsDao.apply {
                baseUrl = this@IntentIntegrator.baseUrl
                accessToken = this@IntentIntegrator.accessToken
                appVersion = this@IntentIntegrator.appVersion
                language = this@IntentIntegrator.language ?: LocaleHelper.DEFAULT_LANGUAGE
                pageSize = this@IntentIntegrator.pageSize ?: Intents.DEFAULT_PAGE_SIZE
                messageId = this@IntentIntegrator.messageId
            }
            startActivityForResult(createIntent(), requestCode)
        }
    }

    /**
     * @param fragment [Fragment] invoking the integration.
     * [.startActivityForResult] will be called on the [Fragment] instead
     * of an [Activity]
     */
    fun forFragment(fragment: Fragment): IntentIntegrator {
        val integrator = IntentIntegrator(fragment.activity as Activity)
        integrator.fragment = fragment
        return integrator
    }

    fun setBaseUrl(baseUrl: String): IntentIntegrator {
        if (baseUrl.isNotEmpty()) {
            this.baseUrl = baseUrl
            addExtra(Intents.BASE_URL, baseUrl)
        }
        return this
    }

    fun setAccessToken(accessToken: String): IntentIntegrator {
        if (accessToken.isNotEmpty()) {
            this.accessToken = accessToken
            addExtra(Intents.ACCESS_TOKEN, accessToken)
        }
        return this
    }

    fun setLanguage(language: String): IntentIntegrator {
        if (language.isNotEmpty()) {
            this.language = language
            addExtra(Intents.LANGUAGE, language)
        }
        return this
    }

    fun setAppVersion(appVersion: String): IntentIntegrator {
        if (appVersion.isNotEmpty()) {
            this.appVersion = appVersion
            addExtra(Intents.APP_VERSION, appVersion)
        }
        return this
    }

    fun setPageSize(pageSize: Int): IntentIntegrator {
        if (pageSize > 0) {
            this.pageSize = pageSize
            addExtra(Intents.PAGE_SIZE, pageSize)
        }
        return this
    }

    /**
     * This will be set if user wants to open details of the message by its id directly.
     */
    fun setMessageId(messageId: Long): IntentIntegrator {
        if (messageId > 0) {
            this.messageId = messageId
            addExtra(Intents.MESSAGE_ID, messageId)
        }
        return this
    }

    private fun addExtra(key: String, value: Any): IntentIntegrator {
        moreExtras[key] = value
        return this
    }

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
        if (defaultActivity == null) {
            defaultActivity = MorniMessageActivity::class.java
        }
        return defaultActivity as Class<*>
    }

    private fun startActivityForResult(intent: Intent, code: Int) {
        if (fragment != null) {
            fragment!!.startActivityForResult(intent, code)
        } else {
            activity.startActivityForResult(intent, code)
        }
    }
}