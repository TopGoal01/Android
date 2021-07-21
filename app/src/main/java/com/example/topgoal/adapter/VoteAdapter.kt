package com.example.topgoal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.topgoal.databinding.RecyclerVoteBinding
import com.example.topgoal.model.Video

class VoteAdapter: RecyclerView.Adapter<VoteHolder>() {
    var voteList = mutableListOf<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteHolder {
        val binding = RecyclerVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VoteHolder(binding)
    }

    override fun onBindViewHolder(holder: VoteHolder, position: Int) {
        val video = voteList[position]
        holder.setVideo(video)
    }

    override fun getItemCount(): Int {
        return voteList.size
    }

}

class VoteHolder(val binding: RecyclerVoteBinding): RecyclerView.ViewHolder(binding.root) {
    fun setVideo(vote: Video){
        binding.txTitle.text = vote.name
        binding.txPlaytime.text = vote.playTime
        // 썸네일 사진
        binding.txUp.text = "${vote.up}"
    }

}