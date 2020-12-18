package com.spbstu.sneakerhunter;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<Element> favoriteElements = new ArrayList<>(Arrays.asList(
            new Element(0,"Puma RS-2K Streaming", 9090,
                    "Puma", "Sneakers", "black",
                    "https://www.asos.com/ru/nike/krasnye-krossovki-nike-air-vapormax-2020-" +
                            "flyknit/prd/22107686?clr=krasnyj&colourwayid=60374145&SearchQuery=&cid=5775",
                    "https://images.asos-media.com/products/krossovki-chernogo-i-belogo-ts" +
                            "vetov-puma-rs-2k-streaming/22016066-1-pumablackpumawhit?$XXL$&wid=513&fit=constrain"),
            new Element(1, "Nike Exosense", 10250.5,
                    "Nike", "Sneakers", "red",
                    "https://www.asos.com/ru/nike/chernye-krossovki-nike-air-max-270/prd/11" +
                            "132622?ctaref=recently+viewed",
                    "https://images.asos-media.com/products/chernye" +
                            "-krossovki-nike-exosense/22795391-1-black?$XXL$&wid=513&fit=constrain"),
            new Element(2,"adidas Originals Rivalry", 10250.2,
                    "adidas", "Sneakers", "green",
                    "https://www.asos.com/ru/nike/zelenye-krossovki-nike-d-ms-x-waffle/prd" +
                            "/20524243?ctaref=recently+viewed",
                    "https://images.asos-media.com/products/nizkie-belye-krossovki-s-te" +
                            "mno-sinimi-poloskami-adidas-originals-rivalry/20635266-1-white?$XXL$&wid" +
                            "=513&fit=constrain")));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler);

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

    private void updateRecycleView() {

        Collections.sort(favoriteElements);

        FavoritesCardAdapter adapter = new FavoritesCardAdapter(favoriteElements);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new FavoritesCardAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Toast toast = Toast.makeText(getContext(), "Хуй соси", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}