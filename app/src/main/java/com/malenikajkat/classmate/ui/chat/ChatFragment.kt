package com.malenikajkat.classmate.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.malenikajkat.classmate.databinding.FragmentChatBinding
import com.malenikajkat.classmate.databinding.ToolbarAddonChatBinding

class ChatFragment : Fragment() {

    companion object {
        const val ARGS_KEY_USER_ID = "bundle_user_id"
        const val ARGS_KEY_OTHER_USER_ID = "bundle_other_user_id"
        const val ARGS_KEY_CHAT_ID = "bundle_other_chat_id"
    }

    private val viewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            requireArguments().getString(ARGS_KEY_USER_ID)!!,
            requireArguments().getString(ARGS_KEY_OTHER_USER_ID)!!,
            requireArguments().getString(ARGS_KEY_CHAT_ID)!!
        )
    }

    private lateinit var viewDataBinding: FragmentChatBinding
    private lateinit var listAdapter: MessagesListAdapter
    private lateinit var listAdapterObserver: RecyclerView.AdapterDataObserver
    private lateinit var toolbarAddonChatBinding: ToolbarAddonChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentChatBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        toolbarAddonChatBinding = ToolbarAddonChatBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupCustomToolbar()
        setupListAdapter()
    }

    private fun setupListAdapter() {
        listAdapter = MessagesListAdapter(viewModel, requireArguments().getString(ARGS_KEY_USER_ID)!!)
        listAdapterObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                messagesRecyclerView.scrollToPosition(positionStart)
            }
        }
        listAdapter.registerAdapterDataObserver(listAdapterObserver)
        viewDataBinding.messagesRecyclerView.adapter = listAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listAdapter.unregisterAdapterDataObserver(listAdapterObserver)
    }
}