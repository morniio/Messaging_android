package com.morni.mornimessagecenter.util.extentions

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Rami El-bouhi on 10,September,2019
 */

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}