package com.example.topgoal.db

import android.util.Log
import com.example.topgoal.BuildConfig
import com.example.topgoal.model.Playlist
import com.example.topgoal.model.Video
import kotlinx.coroutines.*

class Repository {

    val TAG = "Repository"
    private val API_KEY: String = "${BuildConfig.YOUTUBE_API_KEY}"
    private val ChannelId: String = "UCg09VbrEk1N0WrHflZi2rDA"

    suspend fun getVideoTitle(VideoId: String): String{
        val RetCo = CoroutineScope(Dispatchers.IO).async {
            val response: PlayList = PlayListService.client!!.getVideo(
                    API_KEY,
                    "snippet",
                    VideoId
            )
            response
        }
        //val response = RetCo.await()
        //val title :String = response.items.get(0).snippet.title
        return RetCo.await().items.get(0).snippet.title
    }

    suspend fun getVideoDuration(VideoId: String):String{
        val RetCo = CoroutineScope(Dispatchers.IO).async {
            val response: PlayList = PlayListService.client!!.getVideo(
                    API_KEY,
                    "contentDetails",
                    VideoId
            )
            response
        }
        //val response = RetCo.await()
        //val title :String = response.items.get(0).snippet.title
        return getPlayTime(RetCo.await().items.get(0).contentDetails.duration)
    }

    fun getPlayTime(duration:String):String{
        val indexOfM = duration.indexOf('M')
        val indexOfS = duration.indexOf('S')
        var m = if (indexOfM == -1) "0" else duration.substring(2, indexOfM)
        var s = let {
            when {
                indexOfM == -1 -> duration.substring(2, duration.length - 1)
                indexOfS == -1 -> "0"
                else -> duration.substring(indexOfM + 1, indexOfS)
            }
        }
        m = if (m.length == 1) "0".plus(m) else m
        s = if (s.length == 1) "0".plus(s) else s

        return "${m}:${s}"
    }


    suspend fun getVideo(VideoId: String): Video{
        val RetCo = CoroutineScope(Dispatchers.IO).async {
            val response: PlayList = PlayListService.client!!.getVideo(
                API_KEY,
                "snippet",
                VideoId
            )
            response
        }
        val retVideo = RetCo.await().items.get(0)
        return Video(VideoId, retVideo.snippet.title, retVideo.snippet.thumbnails.standard.url)
    }


    suspend fun getPlaylist(): List<Playlist> {
        val RetCo = CoroutineScope(Dispatchers.IO).async {
            val response: PlayList = PlayListService.client!!.getPlayList(
                    API_KEY,
                    "snippet",
                    ChannelId
            )
            response
        }
        val response = RetCo.await()
        val playlist = mutableListOf<Playlist>()
        val ReCo2 = CoroutineScope(Dispatchers.IO).async {
            for (i: Int in 0..response?.pageInfo!!.totalResults - 1) {
                withContext(Dispatchers.Main) {
                    playlist.add(
                            Playlist(
                                    response.items.get(i).id,
                                    response.items.get(i).snippet.title
                            )
                    )
                }
                playlist[i].videoList.addAll(loadVideoList(response.items.get(i).id))
            }
            playlist
        }
        return ReCo2.await()
    }


    suspend fun loadVideoList(id: String): List<Video> {
        val RetCo = CoroutineScope(Dispatchers.IO).async {
            val response: PlayListItems = PlayListService.client!!.getPlayListItems(
                    API_KEY,
                    "snippet",
                    id,
                    50
            )
            response
        }
        val videoList = mutableListOf<Video>()
        val response = RetCo.await()
        val RetCo2 = CoroutineScope(Dispatchers.IO).async {
            val indicator =
                    if (response?.pageInfo!!.totalResults < 50) response?.pageInfo!!.totalResults - 1 else 49
            for (i: Int in 0..indicator) {
                videoList.add(
                        Video(
                                response.items[i].snippet.resourceId.videoId,
                                response.items[i].snippet.title,
                                response.items[i].snippet.thumbnails.default.url
                        )
                )
            }
            videoList
        }
        return RetCo2.await()
    }
}