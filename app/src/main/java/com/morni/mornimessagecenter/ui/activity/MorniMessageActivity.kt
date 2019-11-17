package com.morni.mornimessagecenter.ui.activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.di.Injection
import com.morni.mornimessagecenter.integration.Intents
import com.morni.mornimessagecenter.util.showAlertDialog

/**
 * Created by Rami El-bouhi on 09,September,2019
 */

class MorniMessageActivity : AppCompatActivity() {

    private val prefsDao by lazy { Injection.providePreference(this) }

    override fun attachBaseContext(base: Context) =
        super.attachBaseContext(Injection.provideLocalHelper(base).onAttach())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morni_message)
        prefsDao.baseUrl = "https://api-dev.zayed.io/api/zayed/mobile/v1/"
        prefsDao.accessToken = "eMkjNzEpz48Ur8BDmZXJ"
        prefsDao.language = "ar"
        prefsDao.appVersion = "1.2.1"
        prefsDao.pageSize = 10
        //initializeFromIntent(intent)
    }

    private fun initializeFromIntent(intent: Intent?) {
        if (intent != null) {
            if (intent.extras?.containsKey(Intents.BASE_URL) == true) {
                prefsDao.baseUrl = intent.getStringExtra(Intents.BASE_URL)
            }
            if (intent.extras?.containsKey(Intents.ACCESS_TOKEN) == true) {
                prefsDao.accessToken = intent.getStringExtra(Intents.ACCESS_TOKEN)
            }
            if (intent.extras?.containsKey(Intents.LANGUAGE) == true) {
                prefsDao.language = intent.getStringExtra(Intents.LANGUAGE)
            }
            if (intent.extras?.containsKey(Intents.APP_VERSION) == true) {
                prefsDao.appVersion = intent.getStringExtra(Intents.APP_VERSION)
            }
            if (intent.extras?.containsKey(Intents.PAGE_SIZE) == true) {
                prefsDao.pageSize = intent.getIntExtra(Intents.PAGE_SIZE, Intents.DEFAULT_PAGE_SIZE)
            }
        } else {
            // this case will happen when user start library without use Integration class
            showAlertDialog(
                this,
                getString(R.string.missing_data),
                getString(R.string.not_use_integration_error_msg),
                DialogInterface.OnClickListener({ dialogInterface, i -> this.finish() })
            )
        }
    }

    fun unAuthorizedLogin() {
        val intent = Intent(Intents.START_ACTION)
        intent.putExtra(Intents.STATUS, 401)
        intent.putExtra(Intents.MESSAGE, "Un Authorized Login")
        setResult(Activity.RESULT_OK, intent)
        finish()

    }
}