package com.morni.mornimessagecenter.ui.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.databinding.DefaultFooterLayoutBinding
import com.morni.mornimessagecenter.ui.viewModel.FooterRowViewModel

/**
 * Created by Rami El-bouhi on 10,September,2019
 */
class FooterViewHolder(private val binding: DefaultFooterLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): FooterViewHolder {
            val binding: DefaultFooterLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.default_footer_layout,
                parent,
                false
            )
            binding.txtError.setOnClickListener { retry() }
            return FooterViewHolder(binding)
        }
    }

    private val viewModel = FooterRowViewModel()

    fun bind(context: Context?, statusMorni: MorniApiStatus) {
        viewModel.bind(context, statusMorni)
        binding.viewModel = viewModel
    }
}