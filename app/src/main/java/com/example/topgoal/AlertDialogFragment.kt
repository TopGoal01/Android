package com.example.topgoal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.topgoal.databinding.FragmentAlertDialogBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AlertDialogFragment: DialogFragment() {
    private lateinit var binding: FragmentAlertDialogBinding

    private var message: String? = null
    private var name: String? = null

    private var mainActivity: MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            message = resources.getString(requireArguments().getInt(key_m))
            name = requireArguments().getString(key_n)
        } catch (e: IllegalStateException) { Log.w("AlertDialog", "arguments:failure", e) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertDialogBinding.inflate(inflater, container, false)
        val returnIntent = Intent()
        val user = Firebase.auth.currentUser!!
        binding.txtMessage.text = message
        binding.btnNegative.setOnClickListener { parentFragmentManager.popBackStack() }
        binding.btnPositive.setOnClickListener {
            Firebase.auth.signOut()
            if (name == "delete") {
                try {
                    user.delete()
                } catch (e: FirebaseAuthRecentLoginRequiredException) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(mainActivity?.intent)
                    val credential = GoogleAuthProvider.getCredential(task.getResult(e::class.java).idToken, null)
                    user.reauthenticate(credential).addOnCompleteListener { user.delete() }
                }
            }

            returnIntent.putExtra(name, message)
            mainActivity?.returnToPage(returnIntent)
        }
        binding.constraintLayout.setOnClickListener { parentFragmentManager.popBackStack() }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {
        private const val key_m = "message"
        private const val key_n = "name"

        fun newInstance(message: Int, name: String): AlertDialogFragment {
            val args = Bundle().apply {
                putInt(key_m, message)
                putString(key_n, name)
            }
            val fragment = AlertDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}