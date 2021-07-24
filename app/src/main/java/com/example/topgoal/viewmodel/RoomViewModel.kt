package com.example.topgoal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel(){

    val _currentVideo =  MutableLiveData<String>()
    val currentVideo : LiveData<String> = _currentVideo

    fun loadData() = viewModelScope.launch {
        setVideo("F-KjYNmsi0U")
    }

    fun setVideo(newVideoId: String){
        _currentVideo.value = newVideoId
        _currentVideo.postValue(newVideoId)
    }
}