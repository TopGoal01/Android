package com.example.topgoal.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgoal.adapter.PlaylistDetailAdapter
import com.example.topgoal.databinding.FragmentPlaylistDetailBinding
import com.example.topgoal.model.Video


class PlaylistDetailFragment : Fragment() {

    lateinit var adapter: PlaylistDetailAdapter
    lateinit var binding: FragmentPlaylistDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)

        adapter = PlaylistDetailAdapter()
        var data = mutableListOf<Video>()
        data.add(Video("","Title", "","10:00", 0))
        data.add(Video("", "Title", "", "10:10", 0))
        adapter.videoList = data
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }

}