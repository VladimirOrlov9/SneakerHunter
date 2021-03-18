package com.spbstu.sneakerhunterkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spbstu.sneakerhunterkotlin.R

class ContainerSearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val nextFrag = GenderSelectionFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, nextFrag)
            .commit()
        return inflater.inflate(R.layout.fragment_container_search, container, false)
    }
}