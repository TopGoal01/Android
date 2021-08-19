package com.example.topgoal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.topgoal.databinding.FragmentUserDialogBinding

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
        binding.imageButton2.setOnClickListener { dismiss() }
        binding.button4.setOnClickListener {
            returnIntent.putExtra("logout", binding.button4.text)
            mainActivity?.returnToPage(returnIntent)
        }
        binding.button5.setOnClickListener {
            returnIntent.putExtra("delete", binding.button5.text)
            mainActivity?.returnToPage(returnIntent)
        }
        binding.text3.text = arguments?.getString("text3")
        binding.text4.text = arguments?.getString("text4")
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