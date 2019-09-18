package com.morni.mornimessagecenter.ui.base

import android.app.Application
import com.morni.mornimessagecenter.di.localeHelperModule
import com.morni.mornimessagecenter.di.networkModule
import com.morni.mornimessagecenter.di.preferencesModule
import com.morni.mornimessagecenter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(preferencesModule, localeHelperModule, networkModule, viewModelModule))
        }
    }
}