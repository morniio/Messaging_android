package com.morni.mornimessagecenter.ui.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.databinding.DefaultMorniMessageRowLayoutBinding
import com.morni.mornimessagecenter.ui.viewModel.MorniMessageRowViewModel

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class MorniMessageViewHolder(private val binding: DefaultMorniMessageRowLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val containerView = binding.containerView

    companion object {
        fun create(parent: ViewGroup): MorniMessageViewHolder {
            val binding: DefaultMorniMessageRowLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.default_morni_message_row_layout,
                parent,
                false
            )
            return MorniMessageViewHolder(binding)
        }
    }

    private val viewModel = MorniMessageRowViewModel()

    fun bind(morniMessage: MorniMessage?) {
        viewModel.bind(morniMessage)
        binding.viewModel = viewModel
    }

    fun clear() {
        viewModel.clear()
        binding.viewModel = viewModel
    }
}