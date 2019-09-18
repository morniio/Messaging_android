package com.morni.mornimessagecenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MessageDetailsResponse
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.databinding.DefaultMorniMessageDetailsFragmentBinding
import com.morni.mornimessagecenter.ui.activity.MorniMessageActivity
import com.morni.mornimessagecenter.ui.base.MorniBaseFragment
import com.morni.mornimessagecenter.ui.viewModel.MorniMessageDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MorniMessageDetailsFragment : MorniBaseFragment() {

    companion object {
        fun newInstance() = MorniMessageDetailsFragment()
    }

    val viewModel: MorniMessageDetailsViewModel by viewModel()
    private lateinit var fragmentBinding: DefaultMorniMessageDetailsFragmentBinding
    var messageId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val safeArgs: MorniMessageDetailsFragmentArgs =
                MorniMessageDetailsFragmentArgs.fromBundle(it)
            messageId = safeArgs.messageId
        }

        viewModel.messageDetailsResponse.observe(this, Observer { updateUI(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.default_morni_message_details_fragment, container, false
            )
        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.toolbarContainer.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.messages)

        fragmentBinding.btnRetry.setOnClickListener { viewModel.getMessageDetails(messageId) }
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getMessageDetails(messageId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI(response: MessageDetailsResponse) {
        when (response.morniApiStatus) {
            MorniApiStatus.LOADING -> fragmentBinding.progressBar.visibility = View.VISIBLE
            MorniApiStatus.SUCCESS -> {
                fragmentBinding.progressBar.visibility = View.GONE
                fragmentBinding.txtErrorMsg.visibility = View.GONE
                fragmentBinding.btnRetry.visibility = View.GONE
                fragmentBinding.cvDetails.visibility = View.VISIBLE
                val morniMessage: MorniMessage? = response.data
                if (morniMessage != null) {
                    fragmentBinding.tvTitle.text = morniMessage.title ?: ""
                    fragmentBinding.tvDate.text = morniMessage.createdAt ?: ""
                    fragmentBinding.tvBody.text = morniMessage.body ?: ""
                }
            }
            MorniApiStatus.ERROR -> {
                fragmentBinding.cvDetails.visibility = View.GONE
                fragmentBinding.progressBar.visibility = View.GONE
                fragmentBinding.txtErrorMsg.visibility = View.VISIBLE
                fragmentBinding.btnRetry.visibility = View.VISIBLE
                fragmentBinding.txtErrorMsg.text = response.message ?: ""
            }
            MorniApiStatus.UN_AUTHORIZED -> (activity as MorniMessageActivity).unAuthorizedLogin()
        }
    }
}
