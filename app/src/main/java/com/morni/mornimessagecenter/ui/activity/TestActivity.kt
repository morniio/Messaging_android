package com.morni.mornimessagecenter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.local.PrefsDao
import com.morni.mornimessagecenter.data.remote.AuthInterceptorTest
import com.morni.mornimessagecenter.integration.MorniMessagesSdk

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        /**
         * To do your test, please make this activity as the launcher activity from the manifest.
         * This is only for testing purposes.
         */
        MorniMessagesSdk(this).apply {
            setHttpHeader(AuthInterceptorTest(PrefsDao.getInstance(this@TestActivity)))
            setBaseUrl("") // put base url here..
            setAccessToken("HrRwGf64idZxXW3_SWaa")
            setLanguage("ar")
            setPageSize(10)
            //setMessageId(6936)
            initiate()?.showMessages()
        }
    }
}
