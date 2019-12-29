package com.morni.mornimessagecenter.di

import android.content.Context
import com.morni.mornimessagecenter.data.remote.RetroClient
import com.morni.mornimessagecenter.util.LocaleHelper
import com.morni.mornimessagecenter.data.local.PrefsDao
import com.morni.mornimessagecenter.data.Repository
import com.morni.mornimessagecenter.util.ResourceProvider

object Injection {

    fun provideRepository(context: Context) = Repository(
        ResourceProvider(context),
        RetroClient.getApiService(providePreference(context)),
        providePreference(context)
    )

    /**
     * Singleton function to provide one instance of preference through life of the application.
     */
    fun providePreference(context: Context) = PrefsDao.getInstance(context)

    fun provideLocalHelper(context: Context) = LocaleHelper(providePreference(context), context)

}
