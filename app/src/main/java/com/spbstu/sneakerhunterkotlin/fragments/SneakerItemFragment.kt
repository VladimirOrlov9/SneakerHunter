package com.spbstu.sneakerhunterkotlin.fragments

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.spbstu.sneakerhunterkotlin.HistoryDatabaseHelper
import com.spbstu.sneakerhunterkotlin.R
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import com.spbstu.sneakerhunterkotlin.server_list.SneakersAPI
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class SneakerItemFragment(var sneaker_id: Int) : Fragment() {
    private lateinit var retrofit: Retrofit
    var sneakerItem: Sneaker? = null
    private lateinit var historyDatabaseHelper: SQLiteOpenHelper

    private lateinit var favoritesButton: ImageButton

    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                .baseUrl(SneakersAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }

    private fun  loadImage(urlStr: String?): Bitmap? {
        val urldisplay = "https://$urlStr"
        val url = URL(urldisplay)
        val urlConnection = url.openConnection() as HttpURLConnection
        var bitmap: Bitmap? = null
        try {
            val inputStream = BufferedInputStream(urlConnection.inputStream)
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection.disconnect()
        }
        return bitmap
    }

    private fun executeLoadingImage(urlStr: String?, imageView: ImageView) {
        if (urlStr != null) {
            var result: Bitmap?
            GlobalScope.launch {
                withContext(Dispatchers.IO){
                    result = loadImage(urlStr)
                }
                withContext(Dispatchers.Main){
                    imageView.setImageBitmap(result)
                }
            }
        }
    }

    private fun getSneakerItemFromServer(view: View) {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        val sneakerCall: Call<Sneaker?>? = sneakersAPI.getSneakerById(sneaker_id)
        sneakerCall?.enqueue(object : Callback<Sneaker?> {
            override fun onResponse(
                call: Call<Sneaker?>,
                response: Response<Sneaker?>
            ) {
                if (response.isSuccessful) {
                    sneakerItem = response.body()
                    val imageView =
                        view.findViewById<View>(R.id.sneaker_image_view) as ImageView

                    executeLoadingImage(sneakerItem?.picture?.url, imageView)
                    imageView.contentDescription = sneakerItem?.name

                    val nameTextView = view.findViewById<View>(R.id.sneaker_name) as TextView
                    nameTextView.text = sneakerItem?.name

                    favoritesButton = view.findViewById<View>(R.id.add_to_favorites_button) as ImageButton
                    setFavoriteButtonColor()
                    favoritesButton.setOnClickListener {
                        if (!isFavorite(sneaker_id)) {
                            val shoeValues = ContentValues()
                            shoeValues.put("SNEAKER_KEY", sneaker_id)
                            val historyDatabaseHelper: SQLiteOpenHelper = HistoryDatabaseHelper(context)
                            try {
                                val db = historyDatabaseHelper.writableDatabase
                                db.insertOrThrow("FAVORITES", null, shoeValues)
                                db.close()
                            } catch (e: SQLiteException) {
                                println("This item already in the history list, nothing added.")
                            }
                        } else {
                            try {
                                val db = historyDatabaseHelper.writableDatabase
                                db?.delete("FAVORITES", "SNEAKER_KEY = ?",
                                        arrayOf(sneaker_id.toString()))
                            } catch (ex: SQLiteException) {
                                val toast = Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT)
                                toast.show()
                            }
                        }

                        setFavoriteButtonColor()
                    }

                    val priceTextView = view.findViewById<View>(R.id.sneaker_price_text) as TextView
                    priceTextView.text = sneakerItem?.money

                    val sizesArray = arrayOfNulls<String>(sneakerItem?.size!!.size)
                    for (i in sneakerItem?.size?.indices!!) {
                        sizesArray[i] = sneakerItem?.size?.get(i)?.size
                    }

                    val sizeTextView = view.findViewById<View>(R.id.sneaker_sizes_text) as TextView
                    sizeTextView.text = sizesArray.joinToString()

                    val shopURLButton = view.findViewById<View>(R.id.sneaker_snopurl_button) as Button
                    shopURLButton.text = sneakerItem?.shop?.title
                    shopURLButton.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(sneakerItem?.uri)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(
                call: Call<Sneaker?>,
                t: Throwable
            ) {
                println("fail: $t")
            }
        })
    }

    private fun isFavorite(index: Int): Boolean {
        try {
            val db = historyDatabaseHelper.readableDatabase
            if (db != null) {
                val sql = "SELECT EXISTS (SELECT * FROM FAVORITES WHERE SNEAKER_KEY = $index LIMIT 1)"
                val cursor = db.rawQuery(sql, null)
                cursor.moveToFirst()
                return if (cursor?.getInt(0) == 1) {
                    cursor.close()
                    true
                } else {
                    cursor.close()
                    false
                }
            }
        } catch (ex: SQLiteException) {
            val toast = Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT)
            toast.show()
        }

        return false
    }

    private fun setFavoriteButtonColor() {
        if (isFavorite(sneaker_id)) {
            favoritesButton.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.baseline_favorite_24_liked))
        } else {
            favoritesButton.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.baseline_favorite_24))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_sneaker_item, container, false)
        getSneakerItemFromServer(view)
        val toolbar =
            view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar
            ?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        historyDatabaseHelper = HistoryDatabaseHelper(context)

        val swipeRefresh = view.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            getSneakerItemFromServer(view)

            swipeRefresh.isRefreshing = false
        }

        return view
    }

}