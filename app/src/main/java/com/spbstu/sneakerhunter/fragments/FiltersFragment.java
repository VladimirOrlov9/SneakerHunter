package com.spbstu.sneakerhunter.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.spbstu.sneakerhunter.R;
import com.spbstu.sneakerhunter.server_list.Size;
import com.spbstu.sneakerhunter.server_list.Sneaker;
import com.spbstu.sneakerhunter.server_list.SneakersAPI;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FiltersFragment extends Fragment {

    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Retrofit getClient() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public void createSpinner(View view) {

        SneakersAPI sneakersAPI = getClient().create(SneakersAPI.class);

        Call<List<Size>> sizesCall = sneakersAPI.getSizes();
        sizesCall.enqueue(new Callback<List<Size>>() {
            @Override
            public void onResponse(Call<List<Size>> call, Response<List<Size>> response) {
                if (response.isSuccessful()) {
                    List<Size> sizes = response.body().stream().sorted().collect(Collectors.toList());

                    String[] sizesStrings = new String[sizes.size()];
                    for (int i = 0; i < sizes.size(); i++)
                        sizesStrings[i] = sizes.get(i).getSize();

                    Spinner spinner = view.findViewById(R.id.sizeSpinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, sizesStrings);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Size>> call, Throwable t) {
                System.out.println("fail: " + t);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        createSpinner(view);

        Button confirmButton = (Button) view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner sizeSpinner  = (Spinner) view.findViewById(R.id.sizeSpinner);
                SearchFragment.SORT_SIZE = sizeSpinner.getSelectedItem().toString();
                SearchFragment.IS_SORT_SIZE = true;

                EditText priceFromText = (EditText) view.findViewById(R.id.priceFromEditText);
                EditText priceToText = (EditText) view.findViewById(R.id.priceToEditText);

                if(!priceFromText.getText().toString().equals("")) {
                    SearchFragment.SORT_PRICE_FROM = Integer.parseInt(priceFromText.getText().toString());
                    SearchFragment.IS_SORT_PRICE = true;
                }

                if(!priceToText.getText().toString().equals("")) {
                    SearchFragment.SORT_PRICE_TO = Integer.parseInt(priceToText.getText().toString());
                    SearchFragment.IS_SORT_PRICE = true;
                }

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}