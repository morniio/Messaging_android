package com.morni.mornimessagecenter.util

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import com.morni.mornimessagecenter.data.local.PrefsDao
import java.util.*

/**
 * Created by Rami El-bouhi on 16,September,2019
 */

class LocaleHelper constructor(private val prefsDao: PrefsDao, private val context: Context) {

    companion object {
        const val ARABIC = "ar"
        const val ENGLISH = "en"
        const val DEFAULT_LANGUAGE = ARABIC

        fun getDefaultDeviceLanguage(): String {
            return if (ARABIC.equals(Locale.getDefault().displayLanguage, ignoreCase = true)) {
                ARABIC
            } else {
                ENGLISH
            }
        }
    }

    val language: String?
        get() = persistedData

    private val persistedData: String?
        get() = prefsDao.language

    fun onAttach(): Context {
        val lang = persistedData
        return setLocale(lang!!)
    }

    fun setLocale(language: String): Context {
        persist(language)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(language)
        } else updateResourcesLegacy(language)

    }

    private fun persist(language: String) {
        prefsDao.language = language
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }
}