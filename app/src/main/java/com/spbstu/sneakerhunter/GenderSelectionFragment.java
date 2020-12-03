package com.spbstu.sneakerhunter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class GenderSelectionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gender_selection, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        final View view = getView();

        if (view != null) {
            ImageButton maleButton = view.findViewById(R.id.imageButtonMale);
            maleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SearchFragment nextFrag= new SearchFragment(0);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });

            ImageButton femaleButton = view.findViewById(R.id.imageButtonFemale);
            femaleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SearchFragment nextFrag= new SearchFragment(1);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}