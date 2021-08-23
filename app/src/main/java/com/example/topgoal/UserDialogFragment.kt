package com.example.topgoal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.topgoal.databinding.FragmentUserDialogBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserDialogFragment: DialogFragment() {
    private var _binding: FragmentUserDialogBinding? = null
    private val binding get() = _binding!!

    private var mainActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        val returnIntent = Intent()
        val user = Firebase.auth.currentUser!!
        user.let {
            for (profile in it.providerData) {
                binding.text3.text = profile.displayName
                binding.text4.text = profile.email
                Glide.with(this).load(profile.photoUrl)
                    .circleCrop()
                    .into(binding.imageView)
            }
        }
        binding.imageButton2.setOnClickListener { dismiss() }
        binding.button4.setOnClickListener {
            Firebase.auth.signOut()

            returnIntent.putExtra("logout", binding.button4.text)
            mainActivity?.returnToPage(returnIntent)

            dismiss()
        }
        binding.button5.setOnClickListener {
            Firebase.auth.signOut()
            try {
                user.delete()
            } catch (e: FirebaseAuthRecentLoginRequiredException) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(mainActivity?.intent)
                val credential = GoogleAuthProvider.getCredential(task.getResult(e::class.java).idToken, null)
                user.reauthenticate(credential).addOnCompleteListener { user.delete() }
            }

            returnIntent.putExtra("delete", binding.button5.text)
            mainActivity?.returnToPage(returnIntent)

            dismiss()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}