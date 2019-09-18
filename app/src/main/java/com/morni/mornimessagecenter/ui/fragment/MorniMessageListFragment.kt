package com.morni.mornimessagecenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.paging.PagedList
import com.morni.mornimessagecenter.R
import com.morni.mornimessagecenter.data.model.MorniApiStatus
import com.morni.mornimessagecenter.data.model.MorniApiStatus.*
import com.morni.mornimessagecenter.data.model.MorniMessage
import com.morni.mornimessagecenter.databinding.DefaultMorniMessageListFragmentBinding
import com.morni.mornimessagecenter.ui.activity.MorniMessageActivity
import com.morni.mornimessagecenter.ui.adapter.MessageListAdapter
import com.morni.mornimessagecenter.ui.base.MorniBaseFragment
import com.morni.mornimessagecenter.ui.fragment.MorniMessageListFragmentDirections.actionOpenDetails
import com.morni.mornimessagecenter.ui.viewModel.MorniMessageListViewModel
import com.morni.mornimessagecenter.util.WrapContentLinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class MorniMessageListFragment : MorniBaseFragment() {

    companion object {
        fun newInstance() = MorniMessageListFragment()
    }

    val viewModel: MorniMessageListViewModel by viewModel()
    private lateinit var fragmentBinding: DefaultMorniMessageListFragmentBinding
    private lateinit var messageListAdapter: MessageListAdapter
    lateinit var messagesList: PagedList<MorniMessage>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.default_morni_message_list_fragment, container, false
            )

        // set tool bar title and show back icon
        (activity as AppCompatActivity).setSupportActionBar(fragmentBinding.toolbarContainer.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.messages)

        fragmentBinding.swipeContainer.setOnRefreshListener { viewModel.refresh() }

        messageListAdapter = MessageListAdapter({ viewModel.retry() }, { position ->
            view?.let {
                // make message read
                messagesList[position]?.isRead = true
                // open message details screen
                Navigation.findNavController(it)
                    .navigate(actionOpenDetails().setMessageId(messagesList[position]?.id ?: 0))
            }
        })
        fragmentBinding.rvMessages.layoutManager = WrapContentLinearLayoutManager(this.context)
        fragmentBinding.rvMessages.adapter = messageListAdapter
        fragmentBinding.btnRetry.setOnClickListener { viewModel.refresh() }

        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.statusResponse().observe(this, Observer { response -> updateUI(response) })
        viewModel.messagesResponse().observe(this, Observer {
            messagesList = it
            messageListAdapter.submitList(it)
            fragmentBinding.swipeContainer.isRefreshing = false
        })
    }

    private fun updateUI(response: MorniApiStatus) {
        when (response) {
            INITIAL_LOADING -> fragmentBinding.progressBar.visibility = View.VISIBLE
            LOADING -> messageListAdapter.setState(LOADING)
            INITIAL_SUCCESS -> {
                fragmentBinding.progressBar.visibility = View.GONE
                fragmentBinding.txtErrorMsg.visibility = View.GONE
                fragmentBinding.btnRetry.visibility = View.GONE
            }
            SUCCESS -> {
                messageListAdapter.setState(SUCCESS)
            }
            INITIAL_NO_INTERNET -> {
                fragmentBinding.progressBar.visibility = View.GONE
                fragmentBinding.txtErrorMsg.text = getString(R.string.no_internet_connection)
                fragmentBinding.txtErrorMsg.visibility = View.VISIBLE
                fragmentBinding.btnRetry.visibility = View.VISIBLE
            }
            NO_INTERNET -> {
                messageListAdapter.setState(NO_INTERNET)
            }
            INITIAL_ERROR -> {
                fragmentBinding.progressBar.visibility = View.GONE
                fragmentBinding.txtErrorMsg.text = getString(R.string.error_msg)
                fragmentBinding.txtErrorMsg.visibility = View.VISIBLE
                fragmentBinding.btnRetry.visibility = View.VISIBLE
            }
            ERROR -> {
                messageListAdapter.setState(ERROR)
            }
            EMPTY_DATA -> {
                fragmentBinding.txtErrorMsg.text = getString(R.string.no_data)
                fragmentBinding.txtErrorMsg.visibility = View.VISIBLE
                fragmentBinding.btnRetry.visibility = View.VISIBLE
            }
            UN_AUTHORIZED -> (activity as MorniMessageActivity).unAuthorizedLogin()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
