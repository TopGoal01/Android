package com.example.topgoal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topgoal.db.PlayListService
import com.example.topgoal.db.PlayList
import com.example.topgoal.db.PlayListItems
import com.example.topgoal.model.Playlist
import com.example.topgoal.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayListViewModel: ViewModel() {

    val TAG :String = " PlayListViewModel"
    val PlayList = mutableListOf<Playlist>()
    private val _playList = MutableLiveData<List<Playlist>>()
    var playList: LiveData<List<Playlist>> = _playList

    private val API_KEY: String = "//"
    private val ChannelId: String = "UCg09VbrEk1N0WrHflZi2rDA"

    init {
        viewModelScope.launch {
            val response: PlayList = PlayListService.client!!.getPlayList(
                    API_KEY,
                    "snippet",
                    ChannelId
            )
            for (i: Int in 0..response?.pageInfo!!.totalResults - 1) {
                withContext(Main) {
                    addPlaylist(
                            Playlist(
                                    response.items.get(i).id,
                                    response.items.get(i).snippet.title
                            )
                    )
                }
                loadVideoList(i)
            }
        }
    }


    fun loadVideoList(position: Int) {
        PlayList[position].videoList = mutableListOf()
        viewModelScope.launch {
            val response: PlayListItems = PlayListService.client!!.getPlayListItems(
                API_KEY,
                "snippet",
                PlayList[position].id,
                50
            )
            val indicator =
                if (response?.pageInfo!!.totalResults < 50) response?.pageInfo!!.totalResults - 1 else 49
            for (i: Int in 0..indicator) {
                    PlayList[position].videoList!!.add(
                        Video(
                            response.items[i].snippet.resourceId.videoId,
                            response.items[i].snippet.title,
                            response.items[i].snippet.thumbnails.default.url
                        )
                    )
                    _playList.postValue(PlayList)
            }
        }
    }


    fun addPlaylist(newPlayList: Playlist){
        PlayList.add(newPlayList)
        _playList.value = PlayList
    }

    fun getPlaylist(position: Int): Playlist?{
        return PlayList[position]
    }
}