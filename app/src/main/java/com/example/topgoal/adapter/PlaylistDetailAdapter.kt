package com.example.topgoal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topgoal.databinding.RecyclerPlaylistDetailBinding
import com.example.topgoal.model.Video

class PlaylistDetailAdapter: RecyclerView.Adapter<PlaylistDetailHolder>() {

    var videoList = mutableListOf<Video>()
    private lateinit var itemClickListener: PlaylistDetailAdapter.ItemClickListener

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

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemclickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: PlaylistDetailHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        holder.binding.fabAdd.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }
}


class PlaylistDetailHolder(val binding: RecyclerPlaylistDetailBinding): RecyclerView.ViewHolder(binding.root){
    fun setVideo(Video: Video){
        binding.txTitle.text = Video.name
        binding.txPlaytime.text = Video.playTime
        Glide.with(itemView)
            .load(Video.thumbnail)
            .into(binding.thumbnail)
    }
}