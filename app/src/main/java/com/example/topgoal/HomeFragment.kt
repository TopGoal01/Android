package com.example.topgoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.example.topgoal.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnUser.setOnClickListener {
            startFragment(UserDialogFragment(), null)
        }
        binding.btnCreate.setOnClickListener {
            val intent = Intent(requireContext(), RoomActivity::class.java)
            startActivity(intent)
        }
        binding.btnStart.setOnClickListener {
            startFragment(RoomFragment(), "roomFragment")
        }

        Glide.with(this).load(Firebase.auth.currentUser?.photoUrl)
            .circleCrop()
            .into(binding.btnUser)

        return binding.root
    }

    private fun startFragment(fragment: Fragment, name: String?) {
        parentFragmentManager.commit {
            add(android.R.id.content, fragment)
            addToBackStack(name)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
    }
}