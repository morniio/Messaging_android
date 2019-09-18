package com.morni.mornimessagecenter.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.morni.mornimessagecenter.util.LocaleHelper
import org.koin.android.ext.android.inject

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
open class BaseActivity : AppCompatActivity() {

    protected val localeHelper: LocaleHelper by inject()

    override fun attachBaseContext(base: Context) {

        super.attachBaseContext(localeHelper.onAttach())
    }
}