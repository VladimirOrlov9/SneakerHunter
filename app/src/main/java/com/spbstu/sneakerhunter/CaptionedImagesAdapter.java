package com.spbstu.sneakerhunter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private Listener listener;
    private List<Element> elements;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    interface Listener {
        void onClick(int position);
    }

    CaptionedImagesAdapter(List<Element> elements) {
        this.elements = new ArrayList<>();
        this.elements = elements;
    }

    @NonNull
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);

        return new ViewHolder(cardView);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.element_image);
        
        new DownloadImageTask(imageView).execute(elements.get(position).getImageURL());
        imageView.setContentDescription(elements.get(position).getName());

        TextView elementNameTextView = (TextView) cardView.findViewById(R.id.element_name);
        elementNameTextView.setText(elements.get(position).getName());

        TextView elementPriceTextView = (TextView) cardView.findViewById(R.id.element_price);
        elementPriceTextView.setText(elements.get(position).getPriceString());

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
