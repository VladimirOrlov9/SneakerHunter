package com.spbstu.sneakerhunterkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spbstu.sneakerhunterkotlin.R

class ContainerLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val nextFrag = ProfileScreenFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.frame_login_container, nextFrag)
            .commit()
        return inflater.inflate(R.layout.fragment_login_container, container, false)
    }
}