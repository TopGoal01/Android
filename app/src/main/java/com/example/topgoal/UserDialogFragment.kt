package com.example.topgoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.example.topgoal.databinding.FragmentUserDialogBinding
import com.example.topgoal.db.RoomRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserDialogFragment: DialogFragment() {
    private var _binding: FragmentUserDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDialogBinding.inflate(inflater, container, false)

        val view = binding.root


        binding.txtName.text = RoomRepository.userName
        binding.txtEmail.text = RoomRepository.userEmail
        Glide.with(this).load(RoomRepository.userPic)
                .circleCrop()
                .into(binding.imgUser)


        binding.appBar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        binding.btnLogout.setOnClickListener {
            startAlertDialog(R.string.msg_logout, "logout")
        }
        binding.btnDelete.setOnClickListener {
            startAlertDialog(R.string.msg_delete, "delete")
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun startAlertDialog(message: Int, name: String) {
        parentFragmentManager.commit {
            add(android.R.id.content, AlertDialogFragment.newInstance(message, name))
            addToBackStack("AlertDialog")
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
    }
}