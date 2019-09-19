package com.morni.mornimessagecenter.ui.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import kotlinx.android.synthetic.main.default_footer_layout.view.*

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): FooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.default_footer_layout, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return FooterViewHolder(view)
        }
    }


    fun bind(context: Context?, statusMorni: MorniApiStatus) {
        itemView.progress_bar.visibility = when (statusMorni) {
            MorniApiStatus.LOADING -> View.VISIBLE
            else -> View.GONE
        }
        itemView.txt_error.visibility = when (statusMorni) {
            MorniApiStatus.NO_INTERNET -> View.VISIBLE
            MorniApiStatus.ERROR -> View.VISIBLE
            else -> View.GONE
        }
        itemView.txt_error.text = when (statusMorni) {
            MorniApiStatus.NO_INTERNET -> context?.getString(R.string.no_internet_connection_try_again)
            MorniApiStatus.ERROR -> context?.getString(R.string.error_msg_try_again)
            else -> ""
        }
    }
}