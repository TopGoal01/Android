package com.example.topgoal.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgoal.R
import com.example.topgoal.adapter.VoteAdapter
import com.example.topgoal.databinding.FragmentVoteBinding
import com.example.topgoal.viewmodel.VoteViewModel
import androidx.lifecycle.Observer
import com.example.topgoal.model.Video

class VoteFragment : Fragment() {

    val voteVm: VoteViewModel by viewModels({ requireActivity() })
    lateinit var adapter: VoteAdapter
    lateinit var binding: FragmentVoteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vote, container, false)
        binding.lifecycleOwner = this

        adapter = VoteAdapter()
        adapter.voteList =  mutableListOf()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        voteVm.voteList.observe(    viewLifecycleOwner, Observer {
            adapter.voteList = voteVm.VoteList
            adapter.notifyDataSetChanged()
        })

        binding.recyclerView.setWillNotDraw(false)

        return binding.root
    }
}