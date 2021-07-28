package com.example.topgoal.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgoal.R
import com.example.topgoal.adapter.PlaylistAdapter
import com.example.topgoal.databinding.FragmentPlaylistBinding
import com.example.topgoal.viewmodel.PlayListViewModel

class PlaylistFragment : Fragment() {

    lateinit var adapter: PlaylistAdapter
    lateinit var binding: FragmentPlaylistBinding
    val playlistVm: PlayListViewModel by viewModels({ requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)

        adapter = PlaylistAdapter()
        adapter.playlist = playlistVm.PlayList
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        playlistVm.playList.observe(viewLifecycleOwner){
            adapter.playlist = playlistVm.PlayList
            adapter.notifyDataSetChanged()
        }

        adapter.setItemclickListener( object: PlaylistAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val playListDetailFragement: PlaylistDetailFragment = PlaylistDetailFragment()
                val bundle: Bundle = Bundle()
                bundle.putInt("position", position)
                playListDetailFragement.arguments = bundle
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout1, playListDetailFragement).commit()

            }
        })

        return binding.root
    }

}