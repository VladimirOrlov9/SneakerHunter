package com.spbstu.sneakerhunter.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.spbstu.sneakerhunter.MainActivity;
import com.spbstu.sneakerhunter.R;
import com.spbstu.sneakerhunter.server_list.Sneaker;
import com.spbstu.sneakerhunter.server_list.SneakersAPI;

import java.io.InputStream;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SneakerItemFragment extends Fragment {

    private Retrofit retrofit;
    int sneaker_id;
    Sneaker sneakerItem;

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

    public SneakerItemFragment(int sneaker_id) {
        this.sneaker_id = sneaker_id;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = "https://" + urls[0];
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


    private void getSneakerItemFromServer(View view) {
        SneakersAPI sneakersAPI = getClient().create(SneakersAPI.class);
        Call<Sneaker> sneakerCall = sneakersAPI.getSneakerById(sneaker_id);
        sneakerCall.enqueue(new Callback<Sneaker>() {
            @Override
            public void onResponse(Call<Sneaker> call, Response<Sneaker> response) {
                if (response.isSuccessful()) {
                    sneakerItem = response.body();

                    ImageView imageView = (ImageView) view.findViewById(R.id.sneaker_image_view);
                    new SneakerItemFragment.DownloadImageTask(imageView).execute(sneakerItem.getPicture().getUrl());
                    imageView.setContentDescription(sneakerItem.getName());

                    TextView nameTextView = (TextView) view.findViewById(R.id.sneaker_name);
                    nameTextView.setText(sneakerItem.getName());

                    TextView priceTextView = (TextView) view.findViewById(R.id.sneaker_price_text);
                    priceTextView.setText(sneakerItem.getMoney());

                    String[] sizesArray = new String[sneakerItem.getSize().size()];
                    for (int i = 0; i < sneakerItem.getSize().size(); i++) {
                        sizesArray[i] = sneakerItem.getSize().get(i).getSize();
                    }
                    TextView sizeTextView = (TextView) view.findViewById(R.id.sneaker_sizes_text);
                    sizeTextView.setText(Arrays.toString(sizesArray));

                    Button shopURLButton = (Button) view.findViewById(R.id.sneaker_snopurl_button);
                    shopURLButton.setText(sneakerItem.getShop().getTitle());

                    shopURLButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(sneakerItem.getUri()));
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Sneaker> call, Throwable t) {
                System.out.println("fail: " + t);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sneaker_item, container, false);
        getSneakerItemFromServer(view);

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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}