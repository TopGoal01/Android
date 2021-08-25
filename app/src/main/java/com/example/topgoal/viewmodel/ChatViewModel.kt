package com.example.topgoal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgoal.db.RoomRepository
import com.example.topgoal.model.Chat
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
    private val url = "ws://3.38.34.47:8080/websocket/"

    private lateinit var mStompClient :StompClient
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        _chatList.value = ChatList
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        mStompClient.connect()

        compositeDisposable.add(mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent ->
                    when (lifecycleEvent.type) {
                        LifecycleEvent.Type.OPENED -> {
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
                mStompClient.topic("/topic/chat/${RoomRepository.roomId}").subscribe { topicMessage ->
                    val ChatInfo = Gson().fromJson(topicMessage.payload, Chat::class.java)
                    addChat(ChatInfo)
                })
    }

    //send message
    fun send(message: String) {
        val newChat :Chat = Chat(RoomRepository.userName,
                RoomRepository.userPic,
                message,
                getCurrentTime())

        compositeDisposable.add(mStompClient
                .send("/app/message/", makeJsonString(newChat)).subscribe({
                    Log.v(TAG, "message sent")
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

    fun makeJsonString(newchat: Chat):String{
        val gson = Gson()
        val jsonString: String = gson.toJson(newchat)
        return jsonString
    }
}