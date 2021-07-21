package com.example.topgoal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.topgoal.databinding.RecyclerPlaylistDetailBinding
import com.example.topgoal.model.Video

class PlaylistDetailAdapter: RecyclerView.Adapter<PlaylistDetailHolder>() {
    var videoList = mutableListOf<Video>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistDetailHolder {
        val binding = RecyclerPlaylistDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistDetailHolder, position: Int) {
        val _playlist = videoList.get(position)
        holder.setVideo(_playlist)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}


class PlaylistDetailHolder(val binding: RecyclerPlaylistDetailBinding): RecyclerView.ViewHolder(binding.root){
    fun setVideo(Video: Video){
    }
}