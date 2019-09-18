package com.morni.mornimessagecenter.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.ui.adapter.viewHolder.FooterViewHolder
import com.morni.mornimessagecenter.ui.adapter.viewHolder.MorniMessageViewHolder

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MessageListAdapter(private val retry: () -> Unit, val clickListener: (Int) -> Unit) :
    PagedListAdapter<MorniMessage, RecyclerView.ViewHolder>(MorniMessageDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    private lateinit var context: Context
    private var state = MorniApiStatus.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return if (viewType == DATA_VIEW_TYPE) MorniMessageViewHolder.create(parent)
        else FooterViewHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as MorniMessageViewHolder).bind(getItem(position))
            holder.containerView.setOnClickListener {
                clickListener(position)
            }
        } else (holder as FooterViewHolder).bind(context, state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val MorniMessageDiffCallback = object : DiffUtil.ItemCallback<MorniMessage>() {
            override fun areItemsTheSame(oldItem: MorniMessage, newItem: MorniMessage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MorniMessage, newItem: MorniMessage): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == MorniApiStatus.LOADING || state == MorniApiStatus.NO_INTERNET|| state == MorniApiStatus.ERROR)
    }

    fun setState(morniApiStatus: MorniApiStatus) {
        this.state = morniApiStatus
        notifyItemChanged(super.getItemCount())
    }
}