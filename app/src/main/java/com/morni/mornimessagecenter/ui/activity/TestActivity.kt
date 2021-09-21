package com.morni.mornimessagecenter.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morni.mornimessagecenter.R
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
            setBaseUrl("https://api-sandbox.morniksa.com/provider/v1/") // put base url here..
            setAccessToken("QR-dwGWKGl50v4szUoDa")
            setLanguage("en")
            setAppVersion("12.4")
            setStore("Google")
            setAppType("Provider")
            setPageSize(10)
            //setMessageId(6936)
            initiate()?.showMessages()
        }
    }
}
