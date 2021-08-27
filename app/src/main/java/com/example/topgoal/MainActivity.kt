package com.example.topgoal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.topgoal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, HomeFragment()).commit()
    }

    fun returnToPage(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) finishAffinity()
        super.onBackPressed()
    }
}