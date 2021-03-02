package com.spbstu.sneakerhunterkotlin.fragments

import android.database.sqlite.SQLiteDatabase
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
import com.spbstu.sneakerhunterkotlin.adapters.SearchCardAdapter
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import com.spbstu.sneakerhunterkotlin.server_list.SneakersAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class HistoryFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    var adapter: SearchCardAdapter? = null
    private lateinit var db: SQLiteDatabase
    private lateinit var retrofit: Retrofit
    private val elements: MutableList<Sneaker> = ArrayList()
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
        val view: View =
            inflater.inflate(R.layout.fragment_user_viewed, container, false)

        recyclerView = view.findViewById<View>(R.id.history_recycler) as RecyclerView
        adapter = SearchCardAdapter(elements)
        recyclerView!!.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView!!.layoutManager = layoutManager

        adapter!!.setListener(object : SearchCardAdapter.Listener {
            override fun onClick(position: Int) {
                val nextFrag =
                    SneakerItemFragment(elements[position].id!!)
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_login_container, nextFrag, "selectedSneakerFragment")
                    .addToBackStack(null)
                    .commit()
            }
        })
        val toolbar =
            view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        val swipeRefresh = view.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            updateRecycleView()

            swipeRefresh.isRefreshing = false
        }

        updateRecycleView()
        return view
    }

    private fun getSneakerItemFromServer(sneaker_ids: IntArray) {
        elements.clear()
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        for (i in sneaker_ids.indices) {
            val sneakerCall: Call<Sneaker?>? =
                sneakersAPI.getSneakerById(sneaker_ids[i])
            sneakerCall?.enqueue(object : Callback<Sneaker?> {
                override fun onResponse(
                    call: Call<Sneaker?>,
                    response: Response<Sneaker?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { elements.add(it) }
                        adapter?.notifyDataSetChanged()
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

    private fun updateRecycleView() {
        val historyDatabaseHelper: SQLiteOpenHelper = HistoryDatabaseHelper(context)
        try {
            db = historyDatabaseHelper.readableDatabase
            val cursor = db.query(
                    "HISTORY", arrayOf("_id", "SNEAKER_KEY"), null,
                    null, null, null, "_id DESC"
            )
            val ids = IntArray(cursor.count)
            for (i in 0 until cursor.count) {
                cursor.moveToNext()
                ids[i] = cursor.getInt(1)
            }
            cursor.close()
            getSneakerItemFromServer(ids)
        } catch (ex: SQLiteException) {
            val toast = Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT)
            toast.show()
        }
    }

}