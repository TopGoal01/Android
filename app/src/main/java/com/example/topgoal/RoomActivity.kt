package com.example.topgoal

import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.topgoal.add.AddFragment
import com.example.topgoal.databinding.ActivityRoomBinding
import com.example.topgoal.main.MainFragment

class RoomActivity : AppCompatActivity() {

    val binding by lazy { ActivityRoomBinding.inflate(layoutInflater)}
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment()

        binding.btnBack.setOnClickListener {
            alertBack()
        }

        binding.btnChange.setOnClickListener {
            setFragment()
        }

        binding.btnCopy.setOnClickListener {
            val myClipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val myClip: ClipData = ClipData.newPlainText("RoomDomain", "http://github.com/sea1hee")
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(this,"링크가 복사되었습니다.", Toast.LENGTH_LONG).show()
        }
    }

    fun alertBack(){
        val builder = AlertDialog.Builder(this)
            .setMessage("방을 나가시겠습니까?")
            .setPositiveButton("나가기",
                DialogInterface.OnClickListener { dialog, id ->
                    onBackPressed()
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener{ dialog, id ->

                })
            .show()
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
                flag = 2
            }
            2 -> {
                val frameLayout = supportFragmentManager.findFragmentById(R.id.frameLayout)
                transaction.remove(frameLayout!!).commit()
                flag = 1
            }
        }
    }
}