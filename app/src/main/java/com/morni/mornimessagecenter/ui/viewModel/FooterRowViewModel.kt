package com.morni.mornimessagecenter.ui.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MorniApiStatus

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class FooterRowViewModel : ViewModel() {

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMsg: MutableLiveData<String> = MutableLiveData()
    val errorVisibility: MutableLiveData<Int> = MutableLiveData()

    fun bind(context: Context?, statusMorni: MorniApiStatus) {
        loadingVisibility.value = when (statusMorni) {
            MorniApiStatus.LOADING -> View.VISIBLE
            else -> View.GONE
        }
        errorVisibility.value = when (statusMorni) {
            MorniApiStatus.NO_INTERNET -> View.VISIBLE
            MorniApiStatus.ERROR -> View.VISIBLE
            else -> View.GONE
        }
        errorMsg.value = when (statusMorni) {
            MorniApiStatus.NO_INTERNET -> context?.getString(R.string.no_internet_connection_try_again)
            MorniApiStatus.ERROR -> context?.getString(R.string.error_msg_try_again)
            else -> ""
        }
    }
}