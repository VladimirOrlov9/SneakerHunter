package com.spbstu.sneakerhunter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContainerSearchFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GenderSelectionFragment nextFrag = new GenderSelectionFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, nextFrag)
                .commit();

        return inflater.inflate(R.layout.fragment_container_search, container, false);
    }

}