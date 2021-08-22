package com.example.topgoal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.topgoal.databinding.FragmentHomeBinding
import com.example.topgoal.db.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

private const val ARG_NAME = "name"
private const val ARG_EMAIL = "email"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var mainActivity: MainActivity? = null

    private var name: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            email = it.getString(ARG_EMAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.imageButton.setOnClickListener {
            val dialog = UserDialogFragment()
            val bundle = Bundle()
            bundle.putString("text3", name)
            bundle.putString("text4", email)
            dialog.arguments = bundle
            dialog.show(parentFragmentManager, "userDialog")
        }

        binding.button2.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (RoomRepository.postRoom()){
                    val intent = Intent(requireContext(), RoomActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.button3.setOnClickListener {
            parentFragmentManager.commit {
                add(R.id.frameLayout, RoomFragment())
                addToBackStack("roomFragment")
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String?, email: String?) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_EMAIL, email)
                }
            }
    }
}