package com.morni.mornimessagecenter.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

/**
 * Created by Rami El-bouhi on 18,September,2019
 */


fun showAlertDialog(context: Context, title: String, message: String, positiveListener: DialogInterface.OnClickListener): AlertDialog.Builder {
    val alertDialog = AlertDialog.Builder(context)
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setCancelable(false)
    /*alertDialog.setNegativeButton(
        negativeBtn
    ) { dialogInterface, i -> dialogInterface.dismiss() }*/
    alertDialog.setPositiveButton("OK", positiveListener)
    alertDialog.show()
    return alertDialog
}