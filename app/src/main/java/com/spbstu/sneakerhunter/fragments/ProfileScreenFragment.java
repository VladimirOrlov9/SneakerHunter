package com.spbstu.sneakerhunter.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spbstu.sneakerhunter.R;

public class ProfileScreenFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_screen, container, false);

        Button historyButton = (Button) view.findViewById(R.id.history_button);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserViewedFragment nextFrag = new UserViewedFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_login_container, nextFrag, "profile_frag")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}