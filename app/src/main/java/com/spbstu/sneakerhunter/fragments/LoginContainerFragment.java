package com.spbstu.sneakerhunter.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spbstu.sneakerhunter.R;

public class LoginContainerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LoginFragment nextFrag = new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_login_container, nextFrag)
                .commit();

        return inflater.inflate(R.layout.fragment_login_container, container, false);
    }
}