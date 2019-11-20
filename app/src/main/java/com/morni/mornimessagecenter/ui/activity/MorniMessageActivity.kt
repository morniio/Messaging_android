package com.morni.mornimessagecenter.ui.activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.di.Injection
import com.morni.mornimessagecenter.integration.Intents
import com.morni.mornimessagecenter.integration.Intents.Companion.ACCESS_TOKEN
import com.morni.mornimessagecenter.integration.Intents.Companion.APP_VERSION
import com.morni.mornimessagecenter.integration.Intents.Companion.BASE_URL
import com.morni.mornimessagecenter.integration.Intents.Companion.MESSAGE_ID
import com.morni.mornimessagecenter.ui.fragment.MorniMessageListFragmentDirections.*
import com.morni.mornimessagecenter.util.showAlertDialog

/**
 * Created by Rami El-bouhi on 09,September,2019
 */

class MorniMessageActivity : AppCompatActivity() {

    override fun attachBaseContext(base: Context) =
        super.attachBaseContext(Injection.provideLocalHelper(base).onAttach())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morni_message)
        initializeFromIntent(intent)
    }

    private fun initializeFromIntent(intent: Intent?) = intent.run {
        if(!hasValueOf(BASE_URL) || !hasValueOf(ACCESS_TOKEN) || !hasValueOf(APP_VERSION))
            showAlertDialog(
                this@MorniMessageActivity,
                getString(R.string.missing_data),
                getString(R.string.not_use_integration_error_msg),
                DialogInterface.OnClickListener { _, _ -> this@MorniMessageActivity.finish() }
            )
        // This opens details screen when user passes message id only. Otherwise list fragment will be started by default.
        getValueOf(MESSAGE_ID)?.let {
            findNavController(R.id.fragment).navigate(
                actionOpenDetails().setMessageId(it)
            )
        }
    }

    private fun Intent?.hasValueOf(key: String) =
        this != null && extras?.containsKey(key) == true

    private fun Intent?.getValueOf(key: String) =
        if(!hasValueOf(key)) null else this?.getLongExtra(key, 0)

    fun unAuthorizedLogin() {
        val intent = Intent(Intents.START_ACTION)
        intent.putExtra(Intents.STATUS, 401)
        intent.putExtra(Intents.MESSAGE, "Un Authorized Login")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}