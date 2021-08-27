package com.example.topgoal.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.text.SimpleDateFormat
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
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

        registerSubscriptions()
        compositeDisposable.add(mStompClient.lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { lifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.d(TAG, "chat Web Socket Open!")
                        joinMessage()
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.d(TAG, TAG, lifecycleEvent.exception)
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        Log.d(TAG, "chat Web Socket Closed")
                    }
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                    }
                }
            })

        mStompClient.connect()

    }


    fun addChat(newChat: Chat){
        if (newChat.content == "")
            return

        CoroutineScope(Dispatchers.Main).launch {
            ChatList.add(newChat)
            _chatList.value = ChatList
        }
    }

    fun destoryWebSocket(){
        mStompClient.disconnect()
    }

    // open chatting room
    private fun registerSubscriptions() {
        compositeDisposable.add(mStompClient
            .topic("/topic/chat/${RoomRepository.roomId}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ topicMessage ->
                val ChatInfo = Gson().fromJson(topicMessage.payload, SocketChat::class.java)
                addChat(convertSocketToChat(ChatInfo))
                Log.d(TAG, "Receive Message")
            }, {
                Log.d(TAG, "Recieve Message" + it.stackTrace.toString())
            })
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun joinMessage() {
        val newChat = SocketChat(
            "${RoomRepository.userName}님이 입장하셨습니다.",
            RoomRepository.roomId,
            RoomRepository.userId,
            RoomRepository.userPic,
            getCurrentTime(LocalDateTime.now()),
            RoomRepository.userName)

        Log.d(TAG, makeJsonString(newChat))
        compositeDisposable.add(mStompClient
            .send("/app/join", makeJsonString(newChat))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Join Chat")
            }, {
                Log.d(TAG, it.stackTrace.toString())
            })
        )
    }

    //send message
    @RequiresApi(Build.VERSION_CODES.O)
    fun send(message: String) {
        val newChat = SocketChat(
            message,
            RoomRepository.roomId,
            RoomRepository.userId,
            RoomRepository.userPic,
            getCurrentTime(LocalDateTime.now()),
            RoomRepository.userName)

        Log.d(TAG, makeJsonString(newChat))
        compositeDisposable.add(mStompClient
            .send("/app/message", makeJsonString(newChat))
            .subscribe({
                Log.d(TAG, "Send Message")
            }, {
                Log.d(TAG, it.stackTrace.toString())
            })
        )
    }

    fun getCurrentTime(localDateTime: LocalDateTime): String{
        return localDateTime.toString()
    }

    fun getCurrentMinute(curTime: String):String{
        val z = LocalDateTime.parse(curTime)
        val hour = z.hour
        val minute = z.minute
        return "${hour}:${minute}"
    }

    fun makeJsonString(newchat: SocketChat):String{
        val gson = Gson()
        val jsonString: String = gson.toJson(newchat)
        return jsonString
    }

    fun convertSocketToChat(socketChat: SocketChat): Chat{
        return Chat(
            socketChat.userName, socketChat.picUrl, socketChat.message,
                getCurrentMinute(socketChat.localDateTime)
            )
    }
}