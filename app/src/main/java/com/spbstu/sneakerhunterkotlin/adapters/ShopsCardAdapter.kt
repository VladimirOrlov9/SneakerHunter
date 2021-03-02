package com.spbstu.sneakerhunterkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.sneakerhunterkotlin.R
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import java.util.*


class ShopsCardAdapter internal constructor(elementsParam: List<Sneaker>) :
    RecyclerView.Adapter<ShopsCardAdapter.ViewHolder>() {
    private var listener: Listener? = null

    //TODO replace list of elements with new shop class and refactor onBindViewHolder using new list
    private val elements: List<Sneaker> = elementsParam

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    interface Listener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.shops_cardview, parent, false) as CardView
        return ViewHolder(cardView)
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val cardView = holder.cardView
        val shopNameTextView =
            cardView.findViewById<View>(R.id.shop_name) as TextView
        shopNameTextView.setText(elements[position].name)
        val elementPriceTextView =
            cardView.findViewById<View>(R.id.element_price) as TextView
        elementPriceTextView.setText(elements[position].money)
        val elementDeletionButton =
            cardView.findViewById<View>(R.id.element_deletion) as ImageButton
        cardView.setOnClickListener {
            if (listener != null) {
                listener!!.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }

}