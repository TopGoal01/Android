package com.example.topgoal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topgoal.db.Repository
import com.example.topgoal.model.Video
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class YouTubeViewModel : ViewModel(){

    var title = MutableLiveData<String>()

    val searchVideo = MutableLiveData<Video>()
    val _currentVideo =  MutableLiveData<String>()
    val currentVideo : LiveData<String> = _currentVideo

    val repository = Repository()
    init{
        setVideo("eVaHUAdD2XU")
        viewModelScope.launch {
            title.value = repository.getVideoTitle(currentVideo.value.toString())
            repository.getVideoDuration(currentVideo.value.toString())
        }
    }

    fun getThumbnail(url:String){
        val videoId = getVideoId(url)
        viewModelScope.launch {
            val newVideo = repository.getVideo(videoId)
            newVideo.playTime = repository.getVideoDuration(videoId)
            searchVideo.postValue(newVideo)
        }
    }



    fun getVideoId(url: String):String{
        var videoId: String = ""
        val pattern = Pattern.compile( "^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*",
            Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(url ?: "")
        if (matcher.matches()) {
            videoId = matcher.group(1) ?: "" }
        return videoId
    }

    fun isYoutube(url: String):Boolean{
        return getVideoId(url).isNotEmpty()
    }

    fun setVideo(newVideoId: String){
        _currentVideo.value = newVideoId
        _currentVideo.postValue(newVideoId)
        viewModelScope.launch {
            title.value = repository.getVideoTitle(currentVideo.value.toString())
        }
    }
}