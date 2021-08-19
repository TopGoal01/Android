package com.example.topgoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.topgoal.databinding.FragmentRoomDialogBinding

class RoomDialogFragment: DialogFragment() {
    private lateinit var binding: FragmentRoomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.button7.setOnClickListener { dismiss() }
        return view
    }
}