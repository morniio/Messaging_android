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
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.data.model.MorniApiStatus.*
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.di.Injection
import com.morni.mornimessagecenter.ui.activity.MorniMessageActivity
import com.morni.mornimessagecenter.ui.adapter.MessageListAdapter
import com.morni.mornimessagecenter.ui.base.MorniBaseFragment
import com.morni.mornimessagecenter.ui.fragment.MorniMessageListFragmentDirections.actionOpenDetails
import com.morni.mornimessagecenter.ui.viewModel.MorniMessageListViewModel
import com.morni.mornimessagecenter.util.WrapContentLinearLayoutManager

class MorniMessageListFragment : MorniBaseFragment() {

    private val viewModel by lazy {
        MorniMessageListViewModel(Injection.provideRepository(context!!))
    }
    private lateinit var messageListAdapter: MessageListAdapter
    private lateinit var messagesList: PagedList<MorniMessage>
    private var swipeContainer: SwipeRefreshLayout? = null
    private var btnRetry: Button? = null
    private var progressBar: ProgressBar? = null
    private var txtErrorMsg: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? =
            inflater.inflate(R.layout.default_morni_message_list_fragment, container, false)

        // set tool bar title and show back icon
        (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar))
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.messages)

        swipeContainer = view?.findViewById(R.id.swipe_container)
        btnRetry = view?.findViewById(R.id.btn_retry)
        progressBar = view?.findViewById(R.id.progress_bar)
        txtErrorMsg = view?.findViewById(R.id.txt_error_msg)

        messageListAdapter = MessageListAdapter({ viewModel.retry() }, { position ->
            view?.let {
                // make message read
                messagesList[position]?.isRead = true
                // open message details screen
                Navigation.findNavController(it)
                    .navigate(actionOpenDetails().setMessageId(messagesList[position]?.id ?: 0))
            }
        })
        val rvMessages: RecyclerView? = view?.findViewById(R.id.rv_messages)
        rvMessages?.layoutManager = WrapContentLinearLayoutManager(this.context)
        rvMessages?.adapter = messageListAdapter
        rvMessages?.isMotionEventSplittingEnabled = false // Disable 2 clicks on multiple items at same time to prevent crash with NavController.
        swipeContainer?.setOnRefreshListener { viewModel.refresh() }
        btnRetry?.setOnClickListener { viewModel.refresh() }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.statusResponse().observe(this, Observer { response -> updateUI(response) })
        viewModel.messagesResponse().observe(this, Observer {
            messagesList = it
            messageListAdapter.submitList(it)
            swipeContainer?.isRefreshing = false
        })
    }

    private fun updateUI(response: MorniApiStatus) = when (response) {
        INITIAL_LOADING -> progressBar?.visibility = View.VISIBLE
        LOADING -> messageListAdapter.setState(LOADING)
        INITIAL_SUCCESS -> {
            progressBar?.visibility = View.GONE
            txtErrorMsg?.visibility = View.GONE
            btnRetry?.visibility = View.GONE
        }
        SUCCESS -> messageListAdapter.setState(SUCCESS)
        INITIAL_NO_INTERNET -> {
            progressBar?.visibility = View.GONE
            txtErrorMsg?.text = getString(R.string.no_internet_connection)
            txtErrorMsg?.visibility = View.VISIBLE
            btnRetry?.visibility = View.VISIBLE
        }
        NO_INTERNET -> messageListAdapter.setState(NO_INTERNET)
        INITIAL_ERROR -> {
            progressBar?.visibility = View.GONE
            txtErrorMsg?.text = getString(R.string.error_msg)
            txtErrorMsg?.visibility = View.VISIBLE
            btnRetry?.visibility = View.VISIBLE
        }
        ERROR -> messageListAdapter.setState(ERROR)
        EMPTY_DATA -> {
            progressBar?.visibility = View.GONE
            txtErrorMsg?.text = getString(R.string.no_data)
            txtErrorMsg?.visibility = View.VISIBLE
            btnRetry?.visibility = View.VISIBLE
        }
        UN_AUTHORIZED -> (activity as MorniMessageActivity).unAuthorizedLogin()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
