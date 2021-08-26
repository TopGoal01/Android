package com.example.topgoal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgoal.db.RoomRepository
import com.example.topgoal.model.Chat
import com.example.topgoal.model.SocketChat
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.text.SimpleDateFormat


class ChatViewModel: ViewModel() {

    val ChatList = mutableListOf<Chat>()
    private val _chatList = MutableLiveData<List<Chat>>()
    var chatList: LiveData<List<Chat>> = _chatList

    val TAG = "ChatWebSocket"
    private val url = "ws://ec2-3-38-34-47.ap-northeast-2.compute.amazonaws.com:8080/websocket"

    private var mStompClient :StompClient
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        _chatList.value = ChatList
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

        compositeDisposable.add(mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent ->
                    when (lifecycleEvent.type) {
                        LifecycleEvent.Type.OPENED -> {
                            Log.d(TAG,"chat Web Socket Open!")
                            registerSubscriptions()
                        }
                        LifecycleEvent.Type.ERROR -> {
                            Log.d(TAG, TAG, lifecycleEvent.exception)
                        }
                        LifecycleEvent.Type.CLOSED -> {
                        }
                        LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                        }
                    }
                })

        mStompClient.connect()
        joinMessage()
    }


    fun addChat(newChat: Chat){
        if (newChat.content == "")
            return

        ChatList.add(newChat)
        _chatList.value = ChatList
    }

    fun destoryWebSocket(){
        mStompClient.disconnect()
    }

    // open chatting room
    private fun registerSubscriptions() {
        compositeDisposable.add(
                mStompClient.topic("/topic/chat/${RoomRepository.roomId}").subscribe ({ topicMessage->
                    val ChatInfo = Gson().fromJson(topicMessage.payload, SocketChat::class.java)
                    addChat(convertSocketToChat(ChatInfo))
                    Log.d(TAG,"Receive Message")
                        }, {
                    Log.e(TAG, it.stackTrace.toString())
                        }))
    }

    fun joinMessage() {
        val newChat = SocketChat(
                "님이 입장하셨습니다.",
                RoomRepository.roomId,
                RoomRepository.userId,
                RoomRepository.userPic?:"")

        compositeDisposable.add(mStompClient
                .send("/app/join", makeJsonString(newChat)).subscribe({
                    Log.v(TAG, "Join Chat")
                    addChat(convertSocketToChat(newChat))
                }, {
                    Log.e(TAG, it.stackTrace.toString())
                }))
    }

    //send message
    fun send(message: String) {
        val newChat = SocketChat(
                message,
                RoomRepository.roomId,
                RoomRepository.userId,
                RoomRepository.userPic?:"")

        compositeDisposable.add(mStompClient
                .send("/app/message/", makeJsonString(newChat)).subscribe({
                    Log.v(TAG, "Send Message")
                    addChat(convertSocketToChat(newChat))
                }, {
                    Log.e(TAG, it.stackTrace.toString())
                }))
    }

    fun getCurrentTime():String{
        val time = System.currentTimeMillis()
        var sdf = SimpleDateFormat("HH:mm")
        var formattedDate = sdf.format(time)
        return formattedDate
    }

    fun makeJsonString(newchat: SocketChat):String{
        val gson = Gson()
        val jsonString: String = gson.toJson(newchat)
        return jsonString
    }

    fun convertSocketToChat(socketChat : SocketChat): Chat{
        return Chat(socketChat.userID, socketChat.url, socketChat.message, getCurrentTime())
    }
}