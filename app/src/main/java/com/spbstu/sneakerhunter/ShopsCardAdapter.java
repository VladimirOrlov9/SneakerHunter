package com.spbstu.sneakerhunter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShopsCardAdapter extends RecyclerView.Adapter<ShopsCardAdapter.ViewHolder> {

    private ShopsCardAdapter.Listener listener;
    //TODO replace list of elements with new shop class and refactor onBindViewHolder using new list
    private  List<Element> elements;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }

    interface Listener {
        void onClick(int position);
    }

    ShopsCardAdapter(List<Element> elements) {
        this.elements = new ArrayList<>();
        this.elements = elements;
    }

    @NonNull
    @Override
    public ShopsCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shops_cardview, parent, false);

        return new ShopsCardAdapter.ViewHolder(cardView);
    }

    public void setListener(ShopsCardAdapter.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsCardAdapter.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView shopNameTextView = (TextView) cardView.findViewById(R.id.shop_name);
        shopNameTextView.setText(elements.get(position).getName());

        TextView elementPriceTextView = (TextView) cardView.findViewById(R.id.element_price);
        elementPriceTextView.setText(elements.get(position).getPriceString());

        ImageButton elementDeletionButton = (ImageButton) cardView.findViewById(R.id.element_deletion);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }
}
