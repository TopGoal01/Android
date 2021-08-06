package com.example.topgoal.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.topgoal.R
import com.example.topgoal.RoomActivity
import com.example.topgoal.databinding.FragmentLinkBinding
import com.example.topgoal.model.Video
import com.example.topgoal.viewmodel.VoteViewModel
import com.example.topgoal.viewmodel.YouTubeViewModel


class LinkFragment : Fragment() {

    private lateinit var binding : FragmentLinkBinding
    val voteVm: VoteViewModel by viewModels({ requireActivity() })
    val youtubeVm: YouTubeViewModel by viewModels({ requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(inflater, container, false)
        binding.btnAdd.visibility = View.INVISIBLE
        binding.imageView.setImageResource(0)
        binding.txTitle.text = " "

        youtubeVm.searchVideo.observe(viewLifecycleOwner) {
            Glide.with(binding.root).load(youtubeVm.searchVideo.value?.thumbnail)
                .into(binding.imageView)
            binding.txTitle.text = youtubeVm.searchVideo.value?.name
        }

        binding.btnSearch.setOnClickListener{
            if (youtubeVm.isYoutube(binding.edtLink.text.toString())) {
                youtubeVm.getThumbnail(binding.edtLink.text.toString())
                binding.btnAdd.visibility = View.VISIBLE
            }
            else{
                Toast.makeText(requireContext(), "유튜브 링크가 아닙니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAdd.setOnClickListener {
            if (youtubeVm.searchVideo.value?.id != null) {
                voteVm.addVideo(youtubeVm.searchVideo.value!!)
                Toast.makeText(requireContext(), "후보 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                youtubeVm.searchVideo.value = Video()
                (activity as RoomActivity).setFragment()
            }
            else{
                Toast.makeText(requireContext(), "링크를 입력해주세요.", Toast.LENGTH_SHORT).show()

            }

        }

        return binding.root
    }
}