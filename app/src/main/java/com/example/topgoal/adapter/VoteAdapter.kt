package com.example.topgoal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topgoal.databinding.RecyclerVoteBinding
import com.example.topgoal.model.Video

class VoteAdapter: RecyclerView.Adapter<VoteHolder>() {
    var voteList = mutableListOf<Video>()
    private lateinit var itemClickListener: ItemClickListener

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

    interface ItemClickListener {
        fun onClick(view: View, position: Int, isChecked: Boolean)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: VoteHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        holder.binding.btnUp.setOnClickListener{

            if (holder.binding.btnUp.isChecked)
                itemClickListener.onClick(it, position, true)
            else
                itemClickListener.onClick(it, position, false)
        }
    }
}

class VoteHolder(val binding: RecyclerVoteBinding): RecyclerView.ViewHolder(binding.root) {
    fun setVideo(vote: Video){
        binding.txTitle.text = vote.name
        binding.txPlaytime.text = vote.playTime
        binding.txUp.text = "${vote.up}"
        Glide.with(itemView)
            .load(vote.thumbnail)
            .into(binding.thumbnail)

    }

}