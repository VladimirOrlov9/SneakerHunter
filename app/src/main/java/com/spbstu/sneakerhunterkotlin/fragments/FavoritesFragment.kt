package com.spbstu.sneakerhunterkotlin.fragments

import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.spbstu.sneakerhunterkotlin.HistoryDatabaseHelper
import com.spbstu.sneakerhunterkotlin.R
import com.spbstu.sneakerhunterkotlin.adapters.FavoritesCardAdapter
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import com.spbstu.sneakerhunterkotlin.server_list.SneakersAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FavoritesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesCardAdapter
    private val favoriteElements: MutableMap<Int, Sneaker> = mutableMapOf()
    private lateinit var retrofit: Retrofit
    private lateinit var historyDatabaseHelper: SQLiteOpenHelper
    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_favorites, container, false)

        recyclerView = view.findViewById<View>(R.id.search_recycler) as RecyclerView

        val toolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        val swipeRefresh = view.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            loadElementsFromDb()
            swipeRefresh.isRefreshing = false
        }

        adapter = FavoritesCardAdapter(favoriteElements)
        recyclerView.adapter = adapter
        historyDatabaseHelper = HistoryDatabaseHelper(context)

        adapter.setListener(object : FavoritesCardAdapter.Listener {
            override fun onClick(position: Int) {
                val nextFrag = SneakerItemFragment(favoriteElements[position]?.id!!)
                activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_login_container, nextFrag, "selectedSneakerFragment")
                        .addToBackStack(null)
                        .commit()
            }
        }, object : FavoritesCardAdapter.Listener {
            override fun onClick(position: Int) {
                try {
                    val db = historyDatabaseHelper.writableDatabase
                    db?.delete("FAVORITES", "SNEAKER_KEY = ?",
                            arrayOf(favoriteElements[position]?.id.toString()))

                    loadElementsFromDb()
                    adapter.notifyDataSetChanged()
                } catch (ex: SQLiteException) {
                    val toast = Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        })

        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager

        loadElementsFromDb()

        return view
    }

    private fun loadElementsFromDb() {
        try {
            val db = historyDatabaseHelper.readableDatabase
            if (db != null) {
                val cursor = db.query(
                        "FAVORITES", arrayOf("_id", "SNEAKER_KEY"), null,
                        null, "_id", null, "_id DESC"
                )
                val ids = IntArray(cursor.count)
                for (i in 0 until cursor.count) {
                    cursor.moveToNext()
                    ids[i] = cursor.getInt(1)
                }
                cursor.close()

                favoriteElements.clear()
                val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)

                for (i in ids.indices) {

                    val sneakerCall: Call<Sneaker?>? = sneakersAPI.getSneakerById(ids[i])

                    sneakerCall?.enqueue(object : Callback<Sneaker?> {
                        override fun onResponse(
                                call: Call<Sneaker?>,
                                response: Response<Sneaker?>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.let { favoriteElements.put(i, it) }
//                                adapter.setNewList(favoriteElements.toSortedMap().values.toList())
                                adapter.notifyDataSetChanged()
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
            }
        } catch (ex: SQLiteException) {
            val toast = Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}