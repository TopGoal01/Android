package com.example.topgoal.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.topgoal.adapter.ViewPagerAdapter
import com.example.topgoal.databinding.FragmentAddBinding
import com.google.android.material.tabs.TabLayoutMediator

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = listOf(LinkFragment(), PlaylistFragment())
        val adapter = ViewPagerAdapter(requireActivity())
        adapter.fragmentList = fragmentList
        binding.viewPager2.adapter = adapter

        val tabTitle = listOf<String>("LINK", "MY PLAYLIST")
        TabLayoutMediator(binding.tabLayout2, binding.viewPager2){tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}