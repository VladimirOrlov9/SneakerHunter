package com.spbstu.sneakerhunterkotlin.fragments

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.spbstu.sneakerhunterkotlin.FiltersLogic
import com.spbstu.sneakerhunterkotlin.HistoryDatabaseHelper
import com.spbstu.sneakerhunterkotlin.R
import com.spbstu.sneakerhunterkotlin.adapters.SearchCardAdapter
import com.spbstu.sneakerhunterkotlin.server_list.Size
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import com.spbstu.sneakerhunterkotlin.server_list.SneakersAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.stream.Collectors

open class SearchFragment internal constructor(private val gender: String) : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: SearchCardAdapter? = null
    private var retrofit: Retrofit? = null
    private var toggleButtonState = 0 //0 - desc, 1 - asc
    private var elements: List<Sneaker> = ArrayList<Sneaker>()
    private var newElements: List<Sneaker> = ArrayList<Sneaker>()
    private val filtersLogic = FiltersLogic(gender)
    private val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

    private fun parseJSONsFromServer() {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        val sneakers: Call<List<Sneaker>> = sneakersAPI.sneakers

        sneakers.enqueue(object : Callback<List<Sneaker>> {
            override fun onResponse(
                call: Call<List<Sneaker>>,
                response: Response<List<Sneaker>>
            ) {
                if (response.isSuccessful) {
                    elements = response.body() as List<Sneaker>
                    val editTextSearch = view?.findViewById(R.id.query_edit_text) as EditText
                    val text = editTextSearch.text.toString()
                    if (text == "") {
                        updateRecycleView()
                    } else {
                        updateRecycleView(text)
                    }
                }
            }

            override fun onFailure(call: Call<List<Sneaker>>, t: Throwable) {
                println("fail: $t")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        parseJSONsFromServer()
        recyclerView = view.findViewById<View>(R.id.search_recycler) as RecyclerView
        adapter = SearchCardAdapter(newElements)
        recyclerView?.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView?.layoutManager = layoutManager
        adapter?.setListener(object : SearchCardAdapter.Listener {
            override fun onClick(position: Int) {
                val shoeValues = ContentValues()
                shoeValues.put("SNEAKER_KEY", newElements[position].id)
                val historyDatabaseHelper: SQLiteOpenHelper = HistoryDatabaseHelper(context)
                try {
                    val db = historyDatabaseHelper.writableDatabase
                    db.insertOrThrow("HISTORY", null, shoeValues)
                    db.close()
                } catch (e: SQLiteException) {
                    println("This item already in the history list, nothing added.")
                }

                val nextFrag =
                    SneakerItemFragment(newElements[position].id!!)
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit()
            }
        })
        val toolbar =
            view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val sortSpinner = view.findViewById<View>(R.id.sort_spinner) as Spinner
        val editTextSearch = view.findViewById<View>(R.id.query_edit_text) as EditText
        sortSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val text = editTextSearch.text.toString()
                if (text == "") {
                    toggleButtonState = if (position == 1) 0 else 1
                    updateRecycleView()
                } else {
                    toggleButtonState = if (position == 2) 0 else 1
                    updateRecycleView(text)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                val text = editTextSearch.text.toString()
                if (text != "") {
                    updateRecycleView(text)
                }
            }
        })
        val filterButton =
            view.findViewById<View>(R.id.filter_button) as Button
        filterButton.setOnClickListener {
            val nextFrag = FiltersFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }

        val swipeRefresh = view.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            if (editTextSearch.text.isEmpty()) {
                updateRecycleView()
            } else {
                updateRecycleView(editTextSearch.text.toString())
            }

            swipeRefresh.isRefreshing = false
        }

        return view
    }

    private fun updateRecycleView(searchRequest: String) {
        newElements = filtersLogic.filterListWithStringParameter(elements, searchRequest, toggleButtonState)
        adapter?.setNewList(newElements)
    }

    private fun updateRecycleView() {
        newElements = filtersLogic.filterListWithEmptyString(elements, toggleButtonState)
        adapter?.setNewList(newElements)
    }

    override fun onPause() {
        super.onPause()

        IS_SORT_PRICE = false
        IS_SORT_SIZE = false
        IS_SORT_SHOP = false
        IS_SORT_BRAND = false
    }

    companion object {
        var SORT_PRICE_FROM = 0.0
        var SORT_PRICE_TO = 0.0
        var SORT_SIZE = "Не выбрано"
        var SORT_SHOP = "Не выбрано"
        var SORT_BRAND = "Не выбрано"
        var IS_SORT_PRICE = false
        var IS_SORT_SIZE = false
        var IS_SORT_SHOP = false
        var IS_SORT_BRAND = false
    }
}