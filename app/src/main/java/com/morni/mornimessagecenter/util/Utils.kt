package com.morni.mornimessagecenter.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.morni.mornimessagecenter.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rami El-bouhi on 18,September,2019
 */


fun showAlertDialog(
    context: Context,
    title: String,
    message: String,
    positiveListener: DialogInterface.OnClickListener
): AlertDialog.Builder {
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

fun getFormattedDate(context: Context?, isoTime: String?): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    var convertedDate: Date?
    var formattedDate: String? = null
    try {
        convertedDate = sdf.parse(isoTime)
        formattedDate =
            "${SimpleDateFormat("dd/MM/yyyy", Locale.US).format(convertedDate)} ${context?.getString(R.string.at)} ${SimpleDateFormat(
                "HH:mm a", Locale.US
            ).format(convertedDate)}"
    } catch (e: ParseException) {

    }
    return formattedDate ?: ""
}