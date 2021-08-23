package com.example.topgoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.topgoal.databinding.FragmentRoomBinding

class RoomFragment : Fragment() {
    private lateinit var binding: FragmentRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomBinding.inflate(inflater, container, false)

        binding.appBar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        binding.join.setOnClickListener {
            val intent = Intent(requireContext(), RoomActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}