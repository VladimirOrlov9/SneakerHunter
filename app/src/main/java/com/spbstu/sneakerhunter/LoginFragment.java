package com.spbstu.sneakerhunter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final View view = getView();

        View button = (View) view.findViewById(R.id.sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileScreenFragment nextFrag= new ProfileScreenFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_login_container, nextFrag, "login_frag")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}