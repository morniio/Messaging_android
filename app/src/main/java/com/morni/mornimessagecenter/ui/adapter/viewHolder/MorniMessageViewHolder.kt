package com.morni.mornimessagecenter.ui.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MorniMessage
import kotlinx.android.synthetic.main.default_morni_message_row_layout.view.*

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MorniMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): MorniMessageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.default_morni_message_row_layout, parent, false)
            return MorniMessageViewHolder(view)
        }
    }

    fun bind(morniMessage: MorniMessage?) {
        if (morniMessage != null) {
            itemView.tv_title.text = morniMessage.title ?: ""
            itemView.tv_body.text = morniMessage.subTitle ?: ""
            itemView.tv_date.text = morniMessage.createdAt ?: ""
            itemView.img_new.visibility = when {
                morniMessage.isRead == true -> View.GONE
                else -> View.VISIBLE
            }
        } else {
            itemView.tv_title.text = ""
            itemView.tv_body.text = ""
            itemView.tv_date.text = ""
            itemView.img_new.visibility = View.GONE
        }
    }
}