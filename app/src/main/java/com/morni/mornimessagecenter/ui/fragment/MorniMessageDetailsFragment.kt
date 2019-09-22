package com.morni.mornimessagecenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MessageDetailsResponse
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.ui.activity.MorniMessageActivity
import com.morni.mornimessagecenter.ui.base.MorniBaseFragment
import com.morni.mornimessagecenter.ui.viewModel.MorniMessageDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MorniMessageDetailsFragment : MorniBaseFragment() {

    companion object {
        fun newInstance() = MorniMessageDetailsFragment()
    }

    private val viewModel: MorniMessageDetailsViewModel by viewModel()
    var messageId: Long = 0
    private var cvDetails: CardView? = null
    private var btnRetry: Button? = null
    private var progressBar: ProgressBar? = null
    private var txtErrorMsg: TextView? = null
    private var tvTitle: TextView? = null
    private var tvDate: TextView? = null
    private var tvBody: TextView? = null

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
        val view: View? =
            inflater.inflate(R.layout.default_morni_message_details_fragment, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar))
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.messages)

        btnRetry = view?.findViewById(R.id.btn_retry)
        progressBar = view?.findViewById(R.id.progress_bar)
        txtErrorMsg = view?.findViewById(R.id.txt_error_msg)
        tvTitle = view?.findViewById(R.id.tv_title)
        tvDate = view?.findViewById(R.id.tv_date)
        tvBody = view?.findViewById(R.id.tv_body)

        btnRetry?.setOnClickListener { viewModel.getMessageDetails(messageId) }
        return view
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
            MorniApiStatus.LOADING -> progressBar?.visibility = View.VISIBLE
            MorniApiStatus.SUCCESS -> {
                progressBar?.visibility = View.GONE
                txtErrorMsg?.visibility = View.GONE
                btnRetry?.visibility = View.GONE
                cvDetails?.visibility = View.VISIBLE
                val morniMessage: MorniMessage? = response.data
                if (morniMessage != null) {
                    tvTitle?.text = morniMessage.title ?: ""
                    tvDate?.text = morniMessage.createdAt ?: ""
                    tvBody?.text = morniMessage.body ?: ""
                }
            }
            MorniApiStatus.ERROR -> {
                cvDetails?.visibility = View.GONE
                progressBar?.visibility = View.GONE
                txtErrorMsg?.visibility = View.VISIBLE
                btnRetry?.visibility = View.VISIBLE
                txtErrorMsg?.text = response.message ?: ""
            }
            MorniApiStatus.UN_AUTHORIZED -> (activity as MorniMessageActivity).unAuthorizedLogin()
        }
    }
}
