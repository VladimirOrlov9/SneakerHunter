package com.spbstu.sneakerhunter;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Inflater;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;

    private int gender; //0 - male, 1 - female
    private int toggleButtonState = 0; //0 - desc, 1 - asc
    private List<Element> elements = new ArrayList<>(Arrays.asList(
            new Element("https://images.asos-media.com/products/krossovki-chernogo-i-belogo-ts" +
                    "vetov-puma-rs-2k-streaming/22016066-1-pumablackpumawhit?$XXL$&wid=513&fit=constrain",
                    "Puma RS-2K Streaming", 9090),
            new Element("https://images.asos-media.com/products/chernye" +
                    "-krossovki-nike-exosense/22795391-1-black?$XXL$&wid=513&fit=constrain",
                    "Nike Exosense", 10250),
            new Element("https://images.asos-media.com/products/nizkie-belye-krossovki-s" +
                    "-temno-sinimi-poloskami-adidas-originals-rivalry/20635266-1-white?$XXL$&wid=513&fit=constrain",
                    "adidas Originals Rivalry", 6690),
            new Element("https://images.asos-media.com/products/oranzhevye-krossovki-nike" +
                    "-running-wildhorse-6/20562115-1-orange?$XXL$&wid=513&fit=constrain",
                    "Nike Running Wildhorse 6", 10090),
            new Element("https://images.asos-media.com/products/belo-serebristye-krossovki" +
                    "-puma-ralph-sampson/21955991-1-pumasilverpumawhi?$XXL$&wid=513&fit=constrain",
                    "Puma Ralph Sampson", 6290),
            new Element("https://images.asos-media.com/products/serye-krossovki-nike-tra" +
                    "ining-air-max-alpha/14877171-1-grey?$XXL$&wid=513&fit=constrain",
                    "Nike Training Air Max Alpha", 6199),
            new Element("https://images.asos-media.com/products/bezhevye-krossovki-adidas" +
                    "-originals-torsion-trdc/21838525-1-beige?$XXL$&wid=513&fit=constrain",
                    "adidas Originals Torsion TRDC", 9590),
            new Element("https://images.asos-media.com/products/krossovki-nike-air-max-2090" +
                    "-se-3m-antratsitovo-serogo-tsveta/20522626-1-grey?$XXL$&wid=513&fit=constrain",
                    "Nike Air Max 2090 SE 3M", 12120),
            new Element("https://images.asos-media.com/products/chernye-vysokie-krossovk" +
                    "i-vans-sk8/12234375-1-black?$XXL$&wid=513&fit=constrain",
                    "Vans Sk8", 5590),
            new Element("https://images.asos-media.com/products/zelenye-krossovki-nike-d-m" +
                    "s-x-waffle/20524243-1-green?$XXL$&wid=513&fit=constrain",
                    "Nike D/MS/X Waffle", 7299),
            new Element("https://images.asos-media.com/products/chernye-krossovki-nike-air" +
                    "-max-270/11132622-1-black?$XXL$&wid=513&fit=constrain",
                    "Nike Air Max 270", 11190),
            new Element("https://images.asos-media.com/products/krossovki-zelenogo-i-sinego" +
                    "-tsveta-puma-rs-2k/20752832-1-pumawhitepeacoat?$XXL$&wid=513&fit=constrain",
                    "Puma RS-2K", 8340)
    ));

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