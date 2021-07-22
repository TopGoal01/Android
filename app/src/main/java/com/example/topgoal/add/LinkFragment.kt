package com.example.topgoal.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.topgoal.R
import com.example.topgoal.RoomActivity
import com.example.topgoal.databinding.FragmentLinkBinding
import com.example.topgoal.model.Video
import com.example.topgoal.viewmodel.VoteViewModel


class LinkFragment : Fragment() {

    private var binding : FragmentLinkBinding? = null
    val voteVm: VoteViewModel by viewModels({ requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(inflater, container, false)


        binding!!.btnAdd.setOnClickListener {
            voteVm.addVideo(Video(null, "예시", null, "에시", 0))
            Toast.makeText(requireContext(),"후보 목록에 추가되었습니다.", Toast.LENGTH_LONG).show()
            (activity as RoomActivity).setFragment()
        }

        return binding!!.root
    }

}