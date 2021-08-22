package com.example.topgoal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgoal.model.Chat

class ChatViewModel: ViewModel() {

    val ChatList = mutableListOf<Chat>()

    private val _chatList = MutableLiveData<List<Chat>>()
    var chatList: LiveData<List<Chat>> = _chatList


    init{
        _chatList.value = ChatList
        // 웹 소켓 연결

    }

    fun addChat(newChat: Chat){
        if (newChat.content == "")
            return

        ChatList.add(newChat)
        _chatList.value = ChatList
    }

    fun getChatList(positon: Int): Chat?{
        return ChatList.get(0)
    }

    fun detroyWebSocket(){
       //웹 소켓 연결 해제
    }
}