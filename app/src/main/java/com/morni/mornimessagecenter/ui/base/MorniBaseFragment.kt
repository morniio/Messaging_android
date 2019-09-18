package com.morni.mornimessagecenter.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Rami El-bouhi on 09,September,2019
 */
open class MorniBaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }
}