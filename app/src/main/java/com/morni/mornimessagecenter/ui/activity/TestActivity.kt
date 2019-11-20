package com.morni.mornimessagecenter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.integration.IntentIntegrator

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        IntentIntegrator(this).apply {
            setBaseUrl("https://api-dev.zayed.io/api/zayed/mobile/v1/")
            setAccessToken("IJfmRd16QlSFUabEHY9iU")
            setAppVersion("1.2.1")
            setLanguage("ar")
            setPageSize(10)
            setMessageIdToOpen(3977)
            initiate()
        }

    }
}
