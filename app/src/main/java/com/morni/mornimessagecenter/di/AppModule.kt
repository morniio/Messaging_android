package com.morni.mornimessagecenter.di

import com.morni.mornimessagecenter.data.remote.AuthInterceptor
import com.morni.mornimessagecenter.data.remote.RetroClient
import com.morni.mornimessagecenter.ui.viewModel.MorniMessageDetailsViewModel
import com.morni.mornimessagecenter.ui.viewModel.MorniMessageListViewModel
import com.morni.mornimessagecenter.util.LocaleHelper
import com.morni.mornimessagecenter.util.PrefsDao
import com.morni.mornimessagecenter.util.Repository
import com.morni.mornimessagecenter.util.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Rami El-bouhi on 09,September,2019
 */

val preferencesModule = module {
    single { PrefsDao(get()) }
}

val localeHelperModule = module {
    single { LocaleHelper(get(), androidContext()) }
}

val networkModule = module {
    single { AuthInterceptor(get()) }
    single { RetroClient.createOkHttpClient(get()) }
    single { RetroClient.createApiService(get(), get()) }

    factory {
        Repository(
            ResourceProvider(androidContext()), get(), get()
        )
    }
}

val viewModelModule = module {
    viewModel { MorniMessageListViewModel(get()) }
    viewModel { MorniMessageDetailsViewModel(get()) }
}