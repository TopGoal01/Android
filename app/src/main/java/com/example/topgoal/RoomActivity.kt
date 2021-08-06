package com.example.topgoal

import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.activity.viewModels
import com.example.topgoal.add.AddFragment
import com.example.topgoal.databinding.ActivityRoomBinding
import com.example.topgoal.main.MainFragment
import com.example.topgoal.viewmodel.YouTubeViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX

class RoomActivity : AppCompatActivity() {

    private var threadStopflag = true

    private val API_KEY: String = "${BuildConfig.YOUTUBE_API_KEY}"
    val binding by lazy { ActivityRoomBinding.inflate(layoutInflater)}
    var flag = 0

    val youtubeVm: YouTubeViewModel by viewModels()
    var curYouTubePlayer:YouTubePlayer? = null
    private val youtubeListener = object: YouTubePlayer.OnInitializedListener{

        override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
            Toast.makeText(this@RoomActivity, "Content load fail..", Toast.LENGTH_SHORT).show()
        }

        override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer, isReady: Boolean) {
            curYouTubePlayer = youtubePlayer
            if (!isReady) {
                youtubePlayer.setPlaybackEventListener(playbackEventListener)
                youtubePlayer.setPlayerStateChangeListener(object :
                        YouTubePlayer.PlayerStateChangeListener {
                    override fun onAdStarted() {
                    }

                    override fun onLoading() {
                    }

                    override fun onVideoStarted() {}

                    override fun onLoaded(p0: String?) {
                        // 자동 재생
                        youtubePlayer.play()
                    }

                    override fun onVideoEnded() {
                    }

                    override fun onError(p0: YouTubePlayer.ErrorReason?) {
                    }
                })

                youtubePlayer.cueVideo(youtubeVm.currentVideo.value, 123123)
                // 상호작용 플레이어 컨트롤 삭제
                youtubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)

                //전체화면 버튼 숨김
                youtubePlayer.setShowFullscreenButton(false)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnChange.setOnClickListener {
            setFragment()
        }

        val mYoutubePlayerFragment = YouTubePlayerSupportFragmentX()
        with(mYoutubePlayerFragment) {
            initialize(API_KEY, youtubeListener)
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_youtube, mYoutubePlayerFragment as androidx.fragment.app.Fragment)
            commit()
        }

        youtubeVm.currentVideo.observe(this) {
            curYouTubePlayer?.loadVideo(youtubeVm.currentVideo.value)
        }

        youtubeVm.title.observe(this) {
            binding.txTitle.text = youtubeVm.title.value
        }

        binding.btnCopy.setOnClickListener {
            val myClipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val myClip: ClipData = ClipData.newPlainText("RoomDomain", "http://github.com/sea1hee")
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(this, "링크가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)
        val dialog = builder.show()

        val yesBtn = dialogView.findViewById<Button>(R.id.btn_yes)
        val noBtn = dialogView.findViewById<Button>(R.id.btn_no)

        yesBtn.setOnClickListener{
            super.onBackPressed()
        }
        noBtn.setOnClickListener{
            dialog.dismiss()
        }
    }

    fun setFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        when(flag){
            0 -> {
                transaction.add(R.id.frameLayout, MainFragment()).commit()
                flag = 1
            }
            1 -> {
                transaction.add(R.id.frameLayout, AddFragment()).commit()
                (supportFragmentManager.findFragmentById(R.id.frameLayout) as MainFragment).setTabVote()
                flag = 2
            }
            2 -> {
                val frameLayout = supportFragmentManager.findFragmentById(R.id.frameLayout)
                transaction.remove(frameLayout!!).commit()
                flag = 1
            }
        }
    }

    private val  playbackEventListener: YouTubePlayer.PlaybackEventListener = object: YouTubePlayer.PlaybackEventListener{

        override fun onSeekTo(p0: Int) {}

        override fun onBuffering(p0: Boolean) {}

        override fun onPlaying() {
            threadStopflag = true
        }

        override fun onStopped() {
        }

        override fun onPaused() {
            threadStopflag = false
        }
    }
}