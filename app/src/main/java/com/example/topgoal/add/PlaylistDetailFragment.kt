package com.example.topgoal.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgoal.RoomActivity
import com.example.topgoal.adapter.PlaylistDetailAdapter
import com.example.topgoal.databinding.FragmentPlaylistDetailBinding
import com.example.topgoal.model.Video
import com.example.topgoal.viewmodel.PlayListViewModel
import com.example.topgoal.viewmodel.VoteViewModel


class PlaylistDetailFragment : Fragment() {

    lateinit var adapter: PlaylistDetailAdapter
    lateinit var binding: FragmentPlaylistDetailBinding
    val voteVm: VoteViewModel by viewModels({ requireActivity() })
    val playlistVm: PlayListViewModel by viewModels({ requireActivity() })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)

        adapter = PlaylistDetailAdapter()
        adapter.videoList = playlistVm.PlayList.get(requireArguments().getInt("position")).videoList!!
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setItemclickListener( object: PlaylistDetailAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                voteVm.addVideo(adapter.videoList[position])
                Toast.makeText(requireContext(), "후보 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                (activity as RoomActivity).setFragment()
            }
        })

        return binding.root
    }

}