package com.example.topgoal.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgoal.R
import com.example.topgoal.adapter.ChatAdapter
import com.example.topgoal.databinding.FragmentChatBinding
import com.example.topgoal.viewmodel.ChatViewModel
import androidx.lifecycle.Observer
import com.example.topgoal.model.Chat
import java.text.SimpleDateFormat

class ChatFragment : Fragment() {

    val chatVm: ChatViewModel by viewModels({ requireActivity() })
    lateinit var adapter: ChatAdapter
    lateinit var binding: FragmentChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.lifecycleOwner = this
        binding.chat = chatVm

        adapter = ChatAdapter()
        adapter.chatList =  mutableListOf()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        chatVm.chatList.observe(    viewLifecycleOwner, Observer {
            adapter.chatList = chatVm.ChatList
            adapter.notifyDataSetChanged()
            if( adapter.itemCount >= 1)
                binding.recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        })


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChat.setOnClickListener {
            val content = binding.edtChat.text.toString()
            chatVm.send(content)
            binding.edtChat.setText(null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        chatVm.destoryWebSocket()
        super.onDestroy()
    }

}