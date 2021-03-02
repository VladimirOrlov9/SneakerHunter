package com.spbstu.sneakerhunterkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.spbstu.sneakerhunterkotlin.R
import com.spbstu.sneakerhunterkotlin.server_list.Brand
import com.spbstu.sneakerhunterkotlin.server_list.Size
import com.spbstu.sneakerhunterkotlin.server_list.SneakersAPI
import io.ktor.utils.io.core.String
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.stream.Collectors

class FiltersFragment : Fragment() {
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                .baseUrl(SneakersAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

    private fun createSpinner(view: View) {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        val sizesCall: Call<List<Size?>?>? = sneakersAPI.sizes

        sizesCall?.enqueue(object : Callback<List<Size?>?> {
            override fun onResponse(call: Call<List<Size?>?>, response: Response<List<Size?>?>) {
                if (response.isSuccessful) {
                    val sizes: MutableList<Size?>? = response.body()!!.stream().sorted()
                        .collect(Collectors.toList())
                    val sizesStrings =
                        arrayOfNulls<String>(sizes!!.size + 1)
                    sizesStrings[0] = "Не выбрано"
                    for (i in sizes.indices) sizesStrings[i + 1] = sizes[i]?.size
                    val spinner = view.findViewById<Spinner>(R.id.sizeSpinner)
                    val adapter = ArrayAdapter(
                        context!!,
                        android.R.layout.simple_spinner_dropdown_item, sizesStrings
                    )
                    spinner.adapter = adapter
                    for (i in 0 until spinner.count) {
                        if (spinner.getItemAtPosition(i)
                                .toString() == SearchFragment.SORT_SIZE
                        ) spinner.setSelection(i)
                    }
                }
            }

            override fun onFailure(call: Call<List<Size?>?>, t: Throwable) {
                println("fail: $t")
            }
        })
    }

    private fun createBrandsSpinner(view: View) {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        val sizesCall: Call<List<Brand?>?>? = sneakersAPI.brands

        sizesCall?.enqueue(object : Callback<List<Brand?>?> {
            override fun onResponse(call: Call<List<Brand?>?>, response: Response<List<Brand?>?>) {
                if (response.isSuccessful) {
                    val brands: MutableList<Brand?>? = response.body()!!.stream().sorted()
                        .collect(Collectors.toList())
                    val sizesStrings =
                        arrayOfNulls<String>(brands!!.size + 1)
                    sizesStrings[0] = "Не выбрано"
                    for (i in brands.indices) sizesStrings[i + 1] = brands[i]?.name
                    val spinner = view.findViewById<Spinner>(R.id.brandsSpinner)
                    val adapter = ArrayAdapter(
                        context!!,
                        android.R.layout.simple_spinner_dropdown_item, sizesStrings
                    )
                    spinner.adapter = adapter
                    for (i in 0 until spinner.count) {
                        if (spinner.getItemAtPosition(i)
                                .toString() == SearchFragment.SORT_BRAND
                        ) spinner.setSelection(i)
                    }
                }
            }

            override fun onFailure(call: Call<List<Brand?>?>, t: Throwable) {
                println("fail: $t")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_filters, container, false)
        val toolbar =
            view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        createSpinner(view)
        createBrandsSpinner(view)
        val shopSpin = view.findViewById<View>(R.id.brandSpinner) as Spinner
        val adapterShop = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Не выбрано", "Asos", "Ali Express")
        )

        shopSpin.adapter = adapterShop
        for (i in 0 until shopSpin.count) {
            if (shopSpin.getItemAtPosition(i).toString() == SearchFragment.SORT_SHOP)
                shopSpin.setSelection(i)
        }

        val sizeSpinner = view.findViewById<View>(R.id.sizeSpinner) as Spinner
        val priceFromText = view.findViewById<View>(R.id.priceFromEditText) as EditText

        val priceToText = view.findViewById<View>(R.id.priceToEditText) as EditText

        val brandSpinner = view.findViewById<View>(R.id.brandsSpinner) as Spinner

        val confirmButton = view.findViewById<View>(R.id.confirm_button) as Button
        confirmButton.setOnClickListener {
            if (sizeSpinner.selectedItem.toString() != "Не выбрано") {
                SearchFragment.SORT_SIZE = sizeSpinner.selectedItem.toString()
                SearchFragment.IS_SORT_SIZE = true
            }
            if (priceFromText.text.toString() != "") {
                SearchFragment.SORT_PRICE_FROM = priceFromText.text.toString().toDouble()
                SearchFragment.IS_SORT_PRICE = true
            }
            if (priceToText.text.toString() != "") {
                SearchFragment.SORT_PRICE_TO = priceToText.text.toString().toDouble()
                SearchFragment.IS_SORT_PRICE = true
            }
            if (shopSpin.selectedItem.toString() != "Не выбрано") {
                SearchFragment.SORT_SHOP = shopSpin.selectedItem.toString()
                SearchFragment.IS_SORT_SHOP = true
            }
            if (brandSpinner.selectedItem.toString() != "Не выбрано") {
                SearchFragment.SORT_BRAND = brandSpinner.selectedItem.toString()
                SearchFragment.IS_SORT_BRAND = true
            }
            requireActivity().supportFragmentManager.popBackStack()
        }

        val resetButton = view.findViewById<View>(R.id.reset_button) as Button
        resetButton.setOnClickListener {
            SearchFragment.IS_SORT_SIZE = false
            SearchFragment.SORT_PRICE_FROM = 0.0
            SearchFragment.IS_SORT_PRICE = false
            SearchFragment.SORT_PRICE_TO = 0.0
            SearchFragment.IS_SORT_SHOP = false
            SearchFragment.IS_SORT_BRAND = false
            sizeSpinner.setSelection(0)
            shopSpin.setSelection(0)
            priceFromText.setText("")
            priceToText.setText("")
            requireActivity().supportFragmentManager.popBackStack()
        }
        return view
    }
}

