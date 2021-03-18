package com.spbstu.sneakerhunterkotlin

import org.junit.Test
import com.google.gson.Gson
import com.spbstu.sneakerhunterkotlin.adapters.FavoritesCardAdapter
import com.spbstu.sneakerhunterkotlin.adapters.SearchCardAdapter
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import org.junit.Assert.*
import org.junit.Before

class AdaptersTest {
    val gson = Gson()
    val list: List<Sneaker> = listOf(gson.fromJson("""{"id":3,"shop":{"id":2,"title":"Ali Express","url":"en-aliexpress.com"},"name":"Men
        Shoes Latin BD Two-Point-Soles Heel Teacher Male Adult 417 Oxford-Cloth Soft-Base","size":[],"users":[],"brand":null,"picture":{"id":1
        ,"url":"//ae01.alicdn.com/kf/H29c7cb2b72984d8e89dff6ec6badd536O.jpg_300x300q75.jpg"},"money":"
        ${'$'}43.19","gender":"Men","uri":"https://m.aliexpress.com/item/32808141217.html?pid=808_0000_0201"}""".trimIndent(), Sneaker::class.java),
        gson.fromJson("""{"id":18,"shop":{"id":2,"title":"Ali Express","url":"en-aliexpress.com"},"name":"Leisure-Shoes High-Top Winter Men's Fashion 
            Comfortable Hard-Wearing Trendy Keep-Warm","size":[],"users":[],"brand":null,"picture":{"id":17,"url":"//ae01.alicdn.com/kf/Hb6c171a2eb024a0a
            a20af29d34376673R.jpg_300x300q75.jpg"},"money":" ${'$'}10.87","gender":"Men","uri":"https://m.aliexpress.com/item/1005001822997348.html?p
            id=808_0000_0201"}""".trimIndent(), Sneaker::class.java),
        gson.fromJson("""{"id":36,"shop":{"id":2,"title":"Ali Express","url":"en-aliexpress.com"},"name":"Skate-Shoes Patines 4-Wheels White Women Two-Li
            ne Adult PU with Artificial","size":[],"users":[],"brand":null,"picture":{"id":35,"url":"//ae01.alicdn.com/kf/Hdbb7faab208f4d9fabbdedaa1db82283G
            .jpg_300x300q75.jpg"},"money":" ${'$'}49.69","gender":"Women","uri":"https://m.aliexpress.com/item/4000143721663.ht
            ml?pid=808_0000_0201"}""".trimIndent(), Sneaker::class.java),
        gson.fromJson("""{"id":42,"shop":{"id":2,"title":"Ali Express","url":"en-aliexpress.com"},"name":"Breathable Shoes Sneakers Men Plus-S
            ize Running Casual Lightweight Men's Fashion New","size":[],"users":[],"brand":null,"picture":{"id":32,"url":"//ae01.alicdn.com/kf/Hf9e3ff7ab
            e5f46419d178f274a7b7cccY.jpg_300x300q75.jpg"},"money":" ${'$'}10.99","gender":"Men","uri":"https://m.aliexpress.com/item/1005001824723990.html?p
            id=808_0000_0201"}""".trimIndent(), Sneaker::class.java),
        gson.fromJson("""{"id":141,"shop":{"id":139,"title":"Asos","url":"https://www.asos.com/us/men/"},
            "name":"Walk London terry bar loafers in brown leather","size":[{"id":131,
            "size":"US 7"},{"id":132,"size":"US 8"},{"id":133,"size":"US 9"},{"id":134,"size":"US 10"},
            {"id":135,"size":"US 11"},{"id":136,"size":"US 12"},{"id":137,"size":"US 13"}],"users":[],
            "brand":{"id":138,"name":"WALK LONDON"},"picture":{"id":140,"url":"images.asos-media.com/
            products/walk-london-terry-bar-loafers-in-brown-leather/20583273-1-brown"},"money":"${'$'}102.70",
            "gender":"Men","uri":"https://www.asos.com/walk-london/walk-london-terry-bar-loafers-in-brown
            -leather/prd/20583273?colourwayid=60066131"}""".trimIndent(), Sneaker::class.java),
        gson.fromJson("""{"id":153,"shop":{"id":139,"title":"Asos","url":"https://www.asos.com/us/men/"}
            ,"name":"Nike Training SuperRep Go trainers in black","size":[{"id":131,"size":"US 7"},
            {"id":144,"size":"US 7.5"},{"id":132,"size":"US 8"},{"id":145,"size":"US 8.5"},
            {"id":133,"size":"US 9"},{"id":146,"size":"US 9.5"},{"id":134,"size":"US 10"},
            {"id":147,"size":"US 10.5"},{"id":135,"size":"US 11"},{"id":148,"size":"US 11.5"},
            {"id":136,"size":"US 12"},{"id":149,"size":"US 12.5"},{"id":137,"size":"US 13"},
            {"id":150,"size":"US 14"}],"users":[],"brand":{"id":151,"name":"Nike Training"},
            "picture":{"id":152,"url":"images.asos-media.com/products/nike-training-superrep-go-
            trainers-in-black/20590530-1-black"},"money":"${'$'}100.00","gender":"Men",
            "uri":"https://www.asos.com/nike-training/nike-training-superrep-go-trainers-in-black/prd/2
            0590530?colourwayid=60066721"}""".trimIndent(), Sneaker::class.java))
    val favoritesList = mapOf(0 to Sneaker(), 1 to Sneaker(), 2 to Sneaker())
    lateinit var adapter: SearchCardAdapter
    lateinit var favoritesAdapter: FavoritesCardAdapter

    @Before
    fun init() {
        adapter = SearchCardAdapter(list)
        favoritesAdapter = FavoritesCardAdapter(favoritesList)
    }

    @Test
    fun testCountItemsIntoSearchCardAdapter() {
        assertEquals(6, adapter.itemCount)
    }

    @Test
    fun testCountItemsIntoFavoritesCardAdapter() {
        assertEquals(3, favoritesAdapter.itemCount)
    }
}