package com.spbstu.sneakerhunterkotlin

import android.util.Patterns
import android.webkit.URLUtil
import com.spbstu.sneakerhunterkotlin.fragments.SearchFragment
import com.spbstu.sneakerhunterkotlin.server_list.Size
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import java.net.URL
import java.util.*
import java.util.stream.Collectors

class FiltersLogic(private val gender: String) {
    private val WebUrl = Regex("^((ftp|http|https):\\/\\/)[a-zA-Z0-9_-]+(\\.[a-zA-Z]+)+((\\/)[\\w#]+)*(\\/\\w+\\?[a-zA-Z0-9_]+=\\w+(&[a-zA-Z0-9_]+=\\w+)*)?$")

    fun filterListWithStringParameter(list: List<Sneaker>, searchRequest: String, toggleButtonState: Int) : List<Sneaker> {
        val newElement = list
            .stream()
            .filter { value: Sneaker ->
                (value.name!!.toLowerCase(Locale.ROOT).contains(searchRequest.toLowerCase(Locale.ROOT))
                        && value.gender.equals(gender))
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_PRICE) {
                    if (SearchFragment.SORT_PRICE_TO == 0.0)
                        return@filter (SearchFragment.SORT_PRICE_FROM <= c.doubleMoney)
                    else
                        return@filter (SearchFragment.SORT_PRICE_FROM <= c.doubleMoney) && (c.doubleMoney <= SearchFragment.SORT_PRICE_TO)
                } else {
                    return@filter true
                }
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_SIZE) {
                    val sizes: List<Size>? = c.size
                    for (size in sizes!!) {
                        if (size.size.equals(SearchFragment.SORT_SIZE)) {
                            return@filter true
                        }
                    }
                    return@filter false
                } else {
                    return@filter true
                }
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_SHOP) {
                    return@filter SearchFragment.SORT_SHOP == c.shop?.title
                } else {
                    return@filter true
                }
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_BRAND) {
                    if (c.brand != null) return@filter SearchFragment.SORT_BRAND == c.brand
                        ?.name else return@filter false
                } else {
                    return@filter true
                }
            }
            .collect(Collectors.toList())
            .toMutableList()

        return when (toggleButtonState) {
            0 ->                 //desc sort
                newElement.sorted()
            1 ->                 //asc sort
                newElement.sortedDescending()
            else ->
                newElement
        }
    }

    fun filterListWithEmptyString(list: List<Sneaker>, toggleButtonState: Int) : List<Sneaker> {
        var newElements = list
            .stream()
            .filter { value: Sneaker ->
                value.gender.equals(gender)
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_PRICE) {
                    if (SearchFragment.SORT_PRICE_TO == 0.0)
                        return@filter (SearchFragment.SORT_PRICE_FROM <= c.doubleMoney)
                    else
                        return@filter (SearchFragment.SORT_PRICE_FROM <= c.doubleMoney) && (c.doubleMoney <= SearchFragment.SORT_PRICE_TO)
                } else {
                    return@filter true
                }
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_SIZE) {
                    val sizes: List<Size>? = c.size
                    for (size in sizes!!) {
                        if (size.size.equals(SearchFragment.SORT_SIZE)) {
                            return@filter true
                        }
                    }
                    return@filter false
                } else {
                    return@filter true
                }
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_SHOP) {
                    return@filter SearchFragment.SORT_SHOP == c.shop?.title
                } else {
                    return@filter true
                }
            }
            .filter { c: Sneaker ->
                if (SearchFragment.IS_SORT_BRAND) {
                    if (c.brand != null) return@filter SearchFragment.SORT_BRAND == c.brand
                        ?.name else return@filter false
                } else {
                    return@filter true
                }
            }
            .collect(Collectors.toList())
            .toMutableList()

        return when (toggleButtonState) {
            0 ->                 //asc sort
                getAllValidElements(newElements).sorted()
            1 ->                 //desc sort
                getAllValidElements(newElements).sortedDescending()
            else ->
                emptyList()
        }
    }

    private fun getAllValidElements(list: List<Sneaker>): List<Sneaker> =
            list.asSequence()
                    .filter { it.shop?.title != null }
                    .filter { it.shop?.title == "Ali Express" || it.shop?.title == "Asos" }
                    .filter { it.doubleMoney > 0.0 }
                    .filter { it.gender == "Men" || it.gender == "Women" }
                    .filter {
                        if (it.name != null) {
                            it.name!!.isNotEmpty()
                        } else false
                    }
                    .toList()

}