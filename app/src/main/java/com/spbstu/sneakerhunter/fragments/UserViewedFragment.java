package com.spbstu.sneakerhunter.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spbstu.sneakerhunter.R;
import com.spbstu.sneakerhunter.server_list.Sneaker;

import java.util.ArrayList;
import java.util.List;

public class UserViewedFragment extends Fragment {
    private RecyclerView recyclerView;
    private SQLiteDatabase db;

    private List<Sneaker> elements = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_viewed, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.history_recycler);

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

        //updateRecycleView();

        return view;
    }
//
//    private void updateRecycleView() {
//
//        List<Element> newElements = new ArrayList<>();
//        SQLiteOpenHelper historyDatabaseHelper = new HistoryDatabaseHelper(getContext());
//
//        try {
//            db = historyDatabaseHelper.getReadableDatabase();
//
//            Cursor cursor = db.query("HISTORY", new String[]{"_id", "SNEAKER_KEY"}, null,
//                    null, null, null, null);
//
//            while (cursor.moveToNext()) {
//                int key = cursor.getInt(1);
//                for (int i = 0; i < elements.size(); i++) {
//                    if (elements.get(i).getSneakerKey() == key) {
//                        newElements.add(elements.get(i));
//                    }
//                }
//            }
//            cursor.close();
//        } catch (SQLiteException ex) {
//            Toast toast = Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT);
//            toast.show();
//        }
//
//        if (newElements.size() > 0) {
//            Collections.sort(newElements);
//            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(newElements);
//            recyclerView.setAdapter(adapter);
//            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//            recyclerView.setLayoutManager(layoutManager);
//
//            adapter.setListener(new CaptionedImagesAdapter.Listener() {
//                @Override
//                public void onClick(int position) {
//
//                }
//            });
//        }
//    }
}