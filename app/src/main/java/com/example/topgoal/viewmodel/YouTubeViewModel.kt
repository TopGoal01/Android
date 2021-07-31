package com.example.topgoal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topgoal.db.Repository
import kotlinx.coroutines.launch

class YouTubeViewModel : ViewModel(){

    var title = MutableLiveData<String>()

    val _currentVideo =  MutableLiveData<String>()
    val currentVideo : LiveData<String> = _currentVideo

    val repository = Repository()
    init{
        setVideo("eVaHUAdD2XU")
        viewModelScope.launch {
            title.value = repository.getVideoTitle(currentVideo.value.toString())
        }
    }

    fun setVideo(newVideoId: String){
        _currentVideo.value = newVideoId
        _currentVideo.postValue(newVideoId)
        viewModelScope.launch {
            title.value = repository.getVideoTitle(currentVideo.value.toString())
        }
    }
}