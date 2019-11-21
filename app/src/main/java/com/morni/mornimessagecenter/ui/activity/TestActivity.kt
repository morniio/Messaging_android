package com.morni.mornimessagecenter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.morni.mornimessagecenter.MessagingApp
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.integration.IntentIntegrator

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        /**
         * To do your test, please make this activity as the launcher activity from the manifest and
         * uncomment the bellow lines.
         *
         * This is only for testing purposes.
         */
        IntentIntegrator(MessagingApp.getInstance()).apply {
            setBaseUrl("https://api-dev.zayed.io/api/zayed/mobile/v1/")
            setAccessToken("SmA832JezEKzW6c4xAi0")
            setAppVersion("1.2.1")
            setLanguage("ar")
            setPageSize(10)
            //setMessageId(3977)
            initiate()
        }
    }

/*    companion object {
        fun test() {
            IntentIntegrator(this).apply {
                setBaseUrl("https://api-dev.zayed.io/api/zayed/mobile/v1/")
                setAccessToken("SmA832JezEKzW6c4xAi0")
                setAppVersion("1.2.1")
                setLanguage("ar")
                setPageSize(10)
                //setMessageId(3977)
                initiate()
            }
        }
    }*/
}