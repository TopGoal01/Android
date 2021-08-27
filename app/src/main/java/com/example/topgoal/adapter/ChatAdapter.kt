package com.example.topgoal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topgoal.databinding.RecyclerChatBinding
import com.example.topgoal.db.RoomRepository
import com.example.topgoal.model.Chat

class ChatAdapter: RecyclerView.Adapter<Holder>() {
    var chatList = mutableListOf<Chat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val chat = chatList[position]
        holder.setChat(chat)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

}

class Holder(val binding: RecyclerChatBinding): RecyclerView.ViewHolder(binding.root) {
    fun setChat(chat: Chat){
        binding.txName.text = chat.name
        binding.txContent.text = chat.content
        binding.txTime.text = chat.time

        Glide.with(itemView).load(chat.photo)
                ?.circleCrop()
                .into(binding.imageView2)
    }

}