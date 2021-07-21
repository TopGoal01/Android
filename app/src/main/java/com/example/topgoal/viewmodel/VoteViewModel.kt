package com.example.topgoal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgoal.model.Video

class VoteViewModel: ViewModel() {

    val VoteList = mutableListOf<Video>()

    private val _voteList = MutableLiveData<List<Video>>()
    var voteList: LiveData<List<Video>> = _voteList


    init{
        _voteList.value = VoteList
    }

    fun addVideo(newVideo: Video){

        VoteList.add(newVideo)
        _voteList.value = VoteList
    }

    fun sortingList(){

    }

    fun getVideoList(positon: Int): Video?{
        return VoteList.get(0)
    }
}