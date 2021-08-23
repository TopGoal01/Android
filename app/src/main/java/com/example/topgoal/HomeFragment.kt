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
import com.example.topgoal.db.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.user.setOnClickListener {
            parentFragmentManager.commit {
                add(android.R.id.content, UserDialogFragment())
                addToBackStack(null)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }
        }
        binding.create.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (RoomRepository.postRoom()){
                    val intent = Intent(requireContext(), RoomActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        binding.start.setOnClickListener {
            parentFragmentManager.commit {
                add(R.id.frameLayout, RoomFragment())
                addToBackStack("roomFragment")
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }
        }

        Glide.with(this).load(Firebase.auth.currentUser?.photoUrl)
            .circleCrop()
            .into(binding.user)

        return view
    }
}