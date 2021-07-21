package com.example.topgoal.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgoal.R
import com.example.topgoal.adapter.PlaylistAdapter
import com.example.topgoal.databinding.FragmentPlaylistBinding
import com.example.topgoal.model.Playlist

class PlaylistFragment : Fragment() {

    lateinit var adapter: PlaylistAdapter
    lateinit var binding: FragmentPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPlaylistBinding.inflate(inflater, container, false)

        adapter = PlaylistAdapter()
        var data = mutableListOf<Playlist>()
        data.add(Playlist("플레이리스트1", 14))
        data.add(Playlist("플레이리스트2", 30))
        adapter.playlist = data
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setItemclickListener( object: PlaylistAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout1, PlaylistDetailFragment()).commit()
            }
        })

        return binding.root
    }

}