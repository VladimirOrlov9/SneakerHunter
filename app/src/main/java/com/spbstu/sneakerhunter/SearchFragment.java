package com.spbstu.sneakerhunter;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;

    private int gender; //0 - male, 1 - female
    private int toggleButtonState = 0; //0 - desc, 1 - asc
    private List<Element> elements = new ArrayList<>(Arrays.asList(
            new Element(0,"Puma RS-2K Streaming", 9090,
                     "Puma", "Sneakers", "black",
                    "https://www.asos.com/ru/nike/krasnye-krossovki-nike-air-vapormax-2020-" +
                            "flyknit/prd/22107686?clr=krasnyj&colourwayid=60374145&SearchQuery=&cid=5775",
                    "https://images.asos-media.com/products/krossovki-chernogo-i-belogo-ts" +
                            "vetov-puma-rs-2k-streaming/22016066-1-pumablackpumawhit?$XXL$&wid=513&fit=constrain"),
            new Element(3, "Nike Exosense", 1020,
                    "Nike", "Sneakers", "red",
                    "https://www.asos.com/ru/nike/chernye-krossovki-nike-air-max-270/prd/11" +
                            "132622?ctaref=recently+viewed",
                    "https://images.asos-media.com/products/chernye" +
                            "-krossovki-nike-exosense/22795391-1-black?$XXL$&wid=513&fit=constrain"),
            new Element(4, "adidas Originals Rivalry", 660,
                    "adidas", "Sneakers", "green",
                    "https://www.asos.com/ru/nike/zelenye-krossovki-nike-d-ms-x-waffle/prd" +
                            "/20524243?ctaref=recently+viewed",
                    "https://images.asos-media.com/products/nizkie-belye-krossovki-s-te" +
                            "mno-sinimi-poloskami-adidas-originals-rivalry/20635266-1-white?$XXL$&wid" +
                            "=513&fit=constrain")));


    SearchFragment(int genderId) {
        this.gender = genderId;
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

        final EditText editTextSearch = (EditText) view.findViewById(R.id.query_edit_text);

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

        updateRecycleView();
        return view;
    }

    private void updateRecycleView(final String searchRequest) {

        List<Element> newElements = new ArrayList<>();

        for (Element value : elements) {
            if (value.getName().toLowerCase().contains(searchRequest.toLowerCase())) {
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
                    Collections.sort(newElements, Collections.reverseOrder());
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
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(new ArrayList<Element>());
            recyclerView.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void updateRecycleView() {
            switch (toggleButtonState) {
                case 0:
                    //desc sort
                    Collections.sort(elements);
                    break;
                case 1:
                    //asc sort
                    Collections.sort(elements,Collections.reverseOrder());
                    break;
                default:
                    break;
            }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(elements);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                ContentValues shoeValues = new ContentValues();
                shoeValues.put("SNEAKER_KEY", elements.get(position).getSneakerKey());

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