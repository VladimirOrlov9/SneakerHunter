package com.spbstu.sneakerhunterkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.spbstu.sneakerhunterkotlin.R

class GenderSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gender_selection, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val maleButton = view.findViewById<ImageButton>(R.id.imageButtonMale)
            maleButton.setOnClickListener {
                val nextFrag = SearchFragment("Men")
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, nextFrag, "fragmentSearchMen")
                    .addToBackStack(null)
                    .commit()
            }
            val femaleButton = view.findViewById<ImageButton>(R.id.imageButtonFemale)
            femaleButton.setOnClickListener {
                val nextFrag = SearchFragment("Women")
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, nextFrag, "fragmentSearchWomen")
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

}