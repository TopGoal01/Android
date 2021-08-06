package com.example.topgoal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topgoal.db.Repository
import com.example.topgoal.model.Playlist
import kotlinx.coroutines.launch

class PlayListViewModel: ViewModel() {

    val TAG :String = " PlayListViewModel"
    val PlayList = mutableListOf<Playlist>()
    private val _playList = MutableLiveData<List<Playlist>>()
    var playList: LiveData<List<Playlist>> = _playList

    val repository = Repository()

    init {
        viewModelScope.launch {
            PlayList.addAll(repository.getPlaylist().toMutableList())
            _playList.value = PlayList
        }
    }

    fun setPlaylistDuration(){
        viewModelScope.launch {
            for(i in 0..PlayList.size-1){
                for(j in 0..PlayList[i].videoList.size-1){
                    PlayList[i].videoList[j].playTime = repository.getVideoDuration(PlayList[i].videoList[j].id!!)
                }
            }
        }
    }

    fun getPlaylist(position: Int): Playlist?{
        return PlayList[position]
    }
}