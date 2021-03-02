package com.spbstu.sneakerhunterkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.spbstu.sneakerhunterkotlin.R

class ProfileScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_profile_screen, container, false)
        val historyButton =
            view.findViewById<View>(R.id.history_button) as Button
        historyButton.setOnClickListener {
            val nextFrag = HistoryFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_login_container, nextFrag, "historyFragment")
                .addToBackStack(null)
                .commit()
        }

        val favoritesButton = view.findViewById<View>(R.id.favorites_button) as Button
        favoritesButton.setOnClickListener {
            val nextFrag = FavoritesFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_login_container, nextFrag, "favoritesFragment")
                    .addToBackStack(null)
                    .commit()
        }

        return view
    }
}