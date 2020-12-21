package com.spbstu.sneakerhunter.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spbstu.sneakerhunter.HistoryDatabaseHelper;
import com.spbstu.sneakerhunter.R;
import com.spbstu.sneakerhunter.adapters.CaptionedImagesAdapter;
import com.spbstu.sneakerhunter.server_list.Sneaker;
import com.spbstu.sneakerhunter.server_list.SneakersAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewedFragment extends Fragment {
    private RecyclerView recyclerView;
    CaptionedImagesAdapter adapter;
    private SQLiteDatabase db;
    private Retrofit retrofit;
    private List<Sneaker> elements = new ArrayList<>();

    List<Sneaker> sneakerItem = new ArrayList<>();

    private Retrofit getClient() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_viewed, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.history_recycler);
        adapter = new CaptionedImagesAdapter(elements);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                SneakerItemFragment nextFrag= new SneakerItemFragment(elements.get(position).getId());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_login_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        updateRecycleView();

        return view;
    }


    private void getSneakerItemFromServer(int[] sneaker_ids) {
        elements.clear();
        SneakersAPI sneakersAPI = getClient().create(SneakersAPI.class);
        for (int i = 0; i < sneaker_ids.length; i++) {
            Call<Sneaker> sneakerCall = sneakersAPI.getSneakerById(sneaker_ids[i]);
            sneakerCall.enqueue(new Callback<Sneaker>() {
                @Override
                public void onResponse(Call<Sneaker> call, Response<Sneaker> response) {
                    if (response.isSuccessful()) {
                        elements.add(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Sneaker> call, Throwable t) {
                    System.out.println("fail: " + t);
                }
            });
        }

        if (elements.size() > 0) {
            Collections.sort(elements);
        }
    }

    private void updateRecycleView() {

        SQLiteOpenHelper historyDatabaseHelper = new HistoryDatabaseHelper(getContext());

        try {
            db = historyDatabaseHelper.getReadableDatabase();

            Cursor cursor = db.query("HISTORY", new String[]{"_id", "SNEAKER_KEY"}, null,
                    null, null, null, null);

            int[] ids = new int[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ids[i] = cursor.getInt(1);
            }

            cursor.close();

            getSneakerItemFromServer(ids);
        } catch (SQLiteException ex) {
            Toast toast = Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}