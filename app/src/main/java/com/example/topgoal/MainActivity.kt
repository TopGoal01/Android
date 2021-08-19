package com.example.topgoal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.topgoal.databinding.ActivityMainBinding
import com.example.topgoal.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mainFragment = HomeFragment.newInstance(intent.getStringExtra("name"), intent.getStringExtra("email"))
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, mainFragment).commit()


    }

    fun returnToPage(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}