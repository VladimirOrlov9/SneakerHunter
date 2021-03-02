package com.spbstu.sneakerhunterkotlin.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.sneakerhunterkotlin.R
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class FavoritesCardAdapter(elementsParam: Map<Int, Sneaker>) :
    RecyclerView.Adapter<FavoritesCardAdapter.ViewHolder>() {
    private var listener: Listener? = null
    private var deleteElementListener: Listener? = null
    private var elements: Map<Int, Sneaker> = elementsParam

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

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

//    fun setNewList(list: List<Sneaker>) {
//        elements = list.toMutableList()
//        notifyDataSetChanged()
//    }

    private fun executeLoadingImage(urlStr: String?, imageView: ImageView) {
        if (urlStr != null) {
            var result: Bitmap? = null
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

    interface Listener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorites_cardview, parent, false) as CardView
        return ViewHolder(cardView)
    }

    fun setListener(listener: Listener?, deleteElementListener: Listener?) {
        this.listener = listener
        this.deleteElementListener = deleteElementListener
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val cardView = holder.cardView

        val imageView = cardView.findViewById<View>(R.id.element_image) as ImageView
        executeLoadingImage(elements[position]?.picture?.url, imageView)
        imageView.contentDescription = elements[position]?.name

        val elementNameTextView = cardView.findViewById<View>(R.id.element_name) as TextView
        elementNameTextView.text = elements[position]?.name

        val elementPriceTextView = cardView.findViewById<View>(R.id.element_price) as TextView
        elementPriceTextView.text = elements[position]?.money

        val elementDeletionButton = cardView.findViewById<View>(R.id.element_deletion) as ImageButton
        elementDeletionButton.setOnClickListener {
            deleteElementListener?.onClick(position)
        }

        cardView.setOnClickListener {
            listener?.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }

}