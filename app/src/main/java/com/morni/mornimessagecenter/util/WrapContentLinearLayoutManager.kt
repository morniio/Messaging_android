package com.morni.mornimessagecenter.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Rami El-bouhi on 16,September,2019
 */

// Helper class to void RecyclerView crash when chang happens to items
class WrapContentLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {


    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
        }

    }
}