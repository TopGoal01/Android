package com.example.topgoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.topgoal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            if (it.data?.getStringExtra("logout") != null) {
                Toast.makeText(this, R.string.toast_01, Toast.LENGTH_LONG).show()
            }
            else if (it.data?.getStringExtra("delete") != null) {
                Toast.makeText(this, R.string.toast_02, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", "사용자 이름")
        intent.putExtra("email", "이메일")
        intent.putExtra("photoUrl", "사진")
        binding.button.setOnClickListener { startForResult.launch(intent) }

        val customView = CustomView(this)
        binding.constraintLayout.addView(customView)
    }
}

class CustomView(context: Context) : View(context) {
    private val circle01 = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.purple)
        alpha = 200
    }
    private val circle02 = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.gray_light)
        alpha = 130
    }
    private val circle03 = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.purple_bold)
        alpha = 200
    }
    private val circle04 = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.gray)
        alpha = 150
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            drawCircle(170f, 330f, 170f, circle01)
            drawCircle(350f, 260f, 110f, circle02)
            drawCircle(870f, 210f, 130f, circle03)
            drawCircle(830f, 430f, 250f, circle04)
        }
    }
}