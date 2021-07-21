package com.example.topgoal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.topgoal.databinding.RecyclerPlaylistBinding
import com.example.topgoal.model.Playlist

class PlaylistAdapter: RecyclerView.Adapter<PlaylistHolder>() {
    var playlist = mutableListOf<Playlist>()
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistHolder {
        val binding = RecyclerPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistHolder, position: Int) {
        val _playlist = playlist.get(position)
        holder.setPlayList(_playlist)
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemclickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: PlaylistHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }
}


class PlaylistHolder(val binding: RecyclerPlaylistBinding): RecyclerView.ViewHolder(binding.root){
    fun setPlayList(playlist: Playlist){
        binding.txPlaylistName.text = playlist.name
        binding.txCount.text = "동영상 ${playlist.count}개"
    }
}