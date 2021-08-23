package com.example.topgoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.topgoal.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            if (it.data?.getStringExtra("logout") != null) {
                googleSignInClient.signOut()

                Toast.makeText(this, R.string.toast_01, Toast.LENGTH_LONG).show()
            }
            else if (it.data?.getStringExtra("delete") != null) {
                googleSignInClient.revokeAccess()

                Toast.makeText(this, R.string.toast_02, Toast.LENGTH_LONG).show()
            }
        }
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val tag = "LoginActivity"
    private val handleSignInResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            Log.d(tag, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) { Log.w(tag, "GoogleSignIn:failure", e) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { handleSignInResult.launch(googleSignInClient.signInIntent) }

        binding.constraintLayout.addView(CustomView(this))

        // Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Firebase Auth
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        updateUI()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "signInWithCredential:success")
                    updateUI()
                }
                else Log.w(tag, "signInWithCredential:failure", task.exception)
            }
    }

    private fun updateUI() {
        if (Firebase.auth.currentUser != null) {
            startForResult.launch(Intent(this, MainActivity::class.java))
        }
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