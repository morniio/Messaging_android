package com.morni.mornimessagecenter.ui.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morni.mornimessagecenter.data.model.MorniMessage

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MorniMessageRowViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val createdAt = MutableLiveData<String>()
    val newIconVisibility: MutableLiveData<Int> = MutableLiveData()

    fun bind(morniMessage: MorniMessage?) {
        title.value = morniMessage?.title ?: ""
        body.value = morniMessage?.subTitle ?: ""
        createdAt.value = morniMessage?.createdAt ?: ""

        newIconVisibility.value = when {
            morniMessage?.isRead == true -> View.GONE
            else -> View.VISIBLE
        }
        /*newIconVisibility.value = when {
            morniMessage?.isRead -> View.GONE
            else -> View.VISIBLE
        }*/
    }

    fun clear() {
        title.value = ""
        body.value = ""
        createdAt.value = ""
        newIconVisibility.value = View.GONE
    }
}