package com.example.topgoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.topgoal.databinding.FragmentRoomBinding
import com.example.topgoal.db.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_URL = "url"

class RoomFragment : Fragment() {
    private lateinit var binding: FragmentRoomBinding

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomBinding.inflate(inflater, container, false)

        binding.appBar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        binding.button6.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (RoomRepository.postEnter(binding.editText.text.toString())){
                    val intent = Intent(requireContext(), RoomActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(requireContext(), "방을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            RoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
    }
}