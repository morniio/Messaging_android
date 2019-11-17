package com.morni.mornimessagecenter.di

import android.content.Context
import com.morni.mornimessagecenter.data.remote.RetroClient
import com.morni.mornimessagecenter.util.LocaleHelper
import com.morni.mornimessagecenter.util.PrefsDao
import com.morni.mornimessagecenter.util.Repository
import com.morni.mornimessagecenter.util.ResourceProvider

object Injection {

    fun provideRepository(context: Context) = Repository(
        ResourceProvider(context),
        RetroClient.getApiService(PrefsDao(context)),
        PrefsDao(context)
    )

    fun providePreference(context: Context) = PrefsDao(context)

    fun provideLocalHelper(context: Context) = LocaleHelper(PrefsDao(context), context)

}
