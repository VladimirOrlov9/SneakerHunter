package com.spbstu.sneakerhunter.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.spbstu.sneakerhunter.adapters.CaptionedImagesAdapter;
import com.spbstu.sneakerhunter.HistoryDatabaseHelper;
import com.spbstu.sneakerhunter.R;
import com.spbstu.sneakerhunter.server_list.SneakersAPI;
import com.spbstu.sneakerhunter.server_list.Sneaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private Retrofit retrofit;

    private final String gender;
    private int toggleButtonState = 0; //0 - desc, 1 - asc
    private List<Sneaker> elements = new ArrayList<>();


    SearchFragment(String genderId) {
        this.gender = genderId;
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


    public void parseJSONsFromServer() {

        SneakersAPI sneakersAPI = getClient().create(SneakersAPI.class);

        Call<List<Sneaker>> sneakers = sneakersAPI.getSneakers();
        sneakers.enqueue(new Callback<List<Sneaker>>() {
            @Override
            public void onResponse(Call<List<Sneaker>> call, Response<List<Sneaker>> response) {
                if (response.isSuccessful()) {
                    elements = response.body();
                    updateRecycleView();
                }
            }

            @Override
            public void onFailure(Call<List<Sneaker>> call, Throwable t) {
                System.out.println("fail: " + t);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler);
        parseJSONsFromServer();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ToggleButton sortToggleButton = (ToggleButton) view.findViewById(R.id.sort_toggle_button);

        EditText editTextSearch = (EditText) view.findViewById(R.id.query_edit_text);

        sortToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = editTextSearch.getText().toString();

                if (text.equals("")) {
                    if (isChecked) {
                        toggleButtonState = 0;
                    } else {
                        toggleButtonState = 1;
                    }
                    updateRecycleView();
                } else {
                    if (isChecked) {
                        toggleButtonState = 0;
                    } else {
                        toggleButtonState = 1;
                    }
                    updateRecycleView(text);
                }
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editTextSearch.getText().toString();

                if (text.equals("")) {
                    updateRecycleView();
                } else {
                    updateRecycleView(text);
                }
            }
        });


        return view;
    }

    private void updateRecycleView(final String searchRequest) {

        List<Sneaker> newElements = new ArrayList<>();

        for (Sneaker value : elements) {
            if (value.getName().toLowerCase().contains(searchRequest.toLowerCase())
                    && (value.getGender().equals(gender))) {
                newElements.add(value);
            }
        }

        if (newElements.size() > 0) {
            switch (toggleButtonState) {
                case 0:
                    //desc sort
                    Collections.sort(newElements);
                    break;
                case 1:
                    //asc sort
                    newElements.sort(Collections.reverseOrder());
                    break;
                default:
                    break;
            }

            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(newElements);
            recyclerView.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int position) {

                }
            });
        } else {
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(new ArrayList<Sneaker>());
            recyclerView.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void updateRecycleView() {

        List<Sneaker> newElements = new ArrayList<>();

        for (Sneaker value : elements) {
            System.out.println(value.getGender());
            if ((value.getGender().equals(gender))) {
                newElements.add(value);
            }
        }

            switch (toggleButtonState) {
                case 0:
                    //desc sort
                    Collections.sort(newElements);
                    break;
                case 1:
                    //asc sort
                    newElements.sort(Collections.reverseOrder());
                    break;
                default:
                    break;
            }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(newElements);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                ContentValues shoeValues = new ContentValues();
                shoeValues.put("SNEAKER_KEY", elements.get(position).getId());

                SQLiteOpenHelper historyDatabaseHelper = new HistoryDatabaseHelper(getContext());
                try {
                    SQLiteDatabase db =  historyDatabaseHelper.getWritableDatabase();

                    db.insertOrThrow("HISTORY", null, shoeValues);
                    db.close();
                } catch(SQLiteException e) {
                    System.out.println("This item already in the history list, nothing added.");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}