package com.morni.mornimessagecenter.util

import android.content.Context

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
class ResourceProvider(private var context: Context?) {

    fun getString(resId: Int) = context?.getString(resId) ?: ""

}