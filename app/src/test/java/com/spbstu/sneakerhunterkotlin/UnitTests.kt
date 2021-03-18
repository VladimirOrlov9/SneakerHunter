package com.spbstu.sneakerhunterkotlin

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.spbstu.sneakerhunterkotlin.adapters.FavoritesCardAdapter
import com.spbstu.sneakerhunterkotlin.adapters.SearchCardAdapter
import com.spbstu.sneakerhunterkotlin.fragments.SearchFragment
import com.spbstu.sneakerhunterkotlin.server_list.Sneaker
import com.spbstu.sneakerhunterkotlin.server_list.SneakersAPI
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

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

class SearchFragmentTest {
    lateinit var searchFragment: SearchFragment
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

    @Before
    fun setUp() {
        searchFragment = SearchFragment("Men")
    }

    @Test
    fun testCreationListFromGSONs() {
        assertEquals(6, list.size)
    }

    @Test
    fun testSortList() {
        assertEquals(18, searchFragment.filterListWithEmptyString(list)[0].id)
        assertEquals(42, searchFragment.filterListWithEmptyString(list)[1].id)
        assertEquals(3, searchFragment.filterListWithEmptyString(list)[2].id)
    }

    @Test
    fun testAssertOfSneakers() {
        assertTrue(list[0] > list[1])
    }

    @Test
    fun testBrandParameters() {
        assertEquals(138, list[4].brand?.id)
        assertEquals("WALK LONDON", list[4].brand?.name)
    }

    @Test
    fun testBrandCompareTo() {
        assertTrue(list[4].brand!! > list[5].brand!!)
    }

    @Test
    fun testPictureParameters() {
        assertEquals(1, list[0].picture?.id)
        assertEquals("//ae01.alicdn.com/kf/H29c7cb2b72984d8e89dff6ec6badd536O.jpg_300x300q75.jpg",
                list[0].picture?.url)
    }

    @Test
    fun testShopParameters() {
        assertEquals(2, list[0].shop?.id)
        assertEquals("Ali Express", list[0].shop?.title)
        assertEquals("en-aliexpress.com", list[0].shop?.url)
    }

    @Test
    fun testSizeParameters() {
        assertEquals(131, list[4].size?.get(0)?.id)
        assertEquals("US 7", list[4].size?.get(0)?.size)
    }

    @Test
    fun testSizeCompareTo() {
        assertTrue(list[4].size?.get(0)!! < list[4].size?.get(1)!!)
    }

    @Test
    fun testSneakersAPICompanionObject() {
        val sneakersAPI = SneakersAPI
        assertEquals("http://192.168.0.105:8080/", sneakersAPI.URL)
    }

    @Test
    fun testLeastSneakerParameters() {
        assertEquals("Walk London terry bar loafers in brown leather", list[4].name)
        assertEquals("${'$'}102.70", list[4].money)
        assertEquals("""https://www.asos.com/walk-london/walk-london-terry-bar-loafers-in-brown
            -leather/prd/20583273?colourwayid=60066131""".trimIndent(), list[4].uri?.trimIndent())
        assertTrue(102.7 == list[4].doubleMoney)
    }

    @Test
    fun testSortListWithSetPriceTo () {
        SearchFragment.SORT_PRICE_TO = 90.0
        SearchFragment.IS_SORT_PRICE = true
        assertEquals(3, searchFragment.filterListWithEmptyString(list).size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.IS_SORT_PRICE = false
    }

    @Test
    fun testSortListWithSetPriceFromTo () {
        SearchFragment.SORT_PRICE_FROM = 20.0
        SearchFragment.SORT_PRICE_TO = 100.0
        SearchFragment.IS_SORT_PRICE = true
        assertEquals(2, searchFragment.filterListWithEmptyString(list).size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.SORT_PRICE_FROM = 0.0
        SearchFragment.IS_SORT_PRICE = false
    }

    @Test
    fun testSortListWithSetShopName () {
        SearchFragment.SORT_SHOP = "Asos"
        SearchFragment.IS_SORT_SHOP = true
        assertEquals(2, searchFragment.filterListWithEmptyString(list).size)
        SearchFragment.IS_SORT_SHOP = false
    }

    @Test
    fun testSortListWithSetSize () {
        SearchFragment.SORT_SIZE = "US 10"
        SearchFragment.IS_SORT_SIZE = true
        assertEquals(2, searchFragment.filterListWithEmptyString(list).size)
        SearchFragment.IS_SORT_SIZE = false
    }

    @Test
    fun testSortListWithSetSizeAndPrice () {
        SearchFragment.SORT_SIZE = "US 10"
        SearchFragment.SORT_PRICE_TO = 101.0
        SearchFragment.IS_SORT_PRICE = true
        SearchFragment.IS_SORT_SIZE = true
        assertEquals(1, searchFragment.filterListWithEmptyString(list).size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.IS_SORT_PRICE = false
        SearchFragment.IS_SORT_SIZE = false
    }

    @Test
    fun testSortListWithSetShopAndPrice () {
        SearchFragment.SORT_SHOP = "Ali Express"
        SearchFragment.SORT_PRICE_TO = 40.0
        SearchFragment.IS_SORT_PRICE = true
        SearchFragment.IS_SORT_SHOP = true
        assertEquals(2, searchFragment.filterListWithEmptyString(list).size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.IS_SORT_PRICE = false
        SearchFragment.IS_SORT_SHOP = false
    }

    @Test
    fun testSortListWithSetPriceTo_NonEmptyString () {
        SearchFragment.SORT_PRICE_TO = 90.0
        SearchFragment.IS_SORT_PRICE = true
        assertEquals(1, searchFragment.filterListWithStringParameter(list, "ad").size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.IS_SORT_PRICE = false
    }

    @Test
    fun testSortListWithSetPriceFromTo_NonEmptyString () {
        SearchFragment.SORT_PRICE_FROM = 20.0
        SearchFragment.SORT_PRICE_TO = 100.0
        SearchFragment.IS_SORT_PRICE = true
        assertEquals(1, searchFragment.filterListWithStringParameter(list, "ad").size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.SORT_PRICE_FROM = 0.0
        SearchFragment.IS_SORT_PRICE = false
    }

    @Test
    fun testSortListWithSetShopName_NonEmptyString () {
        SearchFragment.SORT_SHOP = "Asos"
        SearchFragment.IS_SORT_SHOP = true
        assertEquals(1, searchFragment.filterListWithStringParameter(list, "f").size)
        SearchFragment.IS_SORT_SHOP = false
    }

    @Test
    fun testSortListWithSetSize_NonEmptyString () {
        SearchFragment.SORT_SIZE = "US 10"
        SearchFragment.IS_SORT_SIZE = true
        assertEquals(1, searchFragment.filterListWithStringParameter(list, "f").size)
        SearchFragment.IS_SORT_SIZE = false
    }

    @Test
    fun testSortListWithSetSizeAndPrice_NonEmptyString () {
        SearchFragment.SORT_SIZE = "US 10"
        SearchFragment.SORT_PRICE_TO = 101.0
        SearchFragment.IS_SORT_PRICE = true
        SearchFragment.IS_SORT_SIZE = true
        assertEquals(0, searchFragment.filterListWithStringParameter(list, "as").size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.IS_SORT_PRICE = false
        SearchFragment.IS_SORT_SIZE = false
    }

    @Test
    fun testSortListWithSetShopAndPrice_NonEmptyString () {
        SearchFragment.SORT_SHOP = "Ali Express"
        SearchFragment.SORT_PRICE_TO = 40.0
        SearchFragment.IS_SORT_PRICE = true
        SearchFragment.IS_SORT_SHOP = true
        assertEquals(2, searchFragment.filterListWithStringParameter(list, "fashion").size)
        SearchFragment.SORT_PRICE_TO = 0.0
        SearchFragment.IS_SORT_PRICE = false
        SearchFragment.IS_SORT_SHOP = false
    }

    @Test
    fun testSortListWithStringParameter() {
        assertEquals(153, searchFragment.filterListWithStringParameter(list, "nike")[0].id)
        assertEquals(1, searchFragment.filterListWithStringParameter(list, "nike").size)
    }

}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class HistoryDatabaseHelperTest {
    lateinit var helper: HistoryDatabaseHelper

    @Before
    fun setUp() {
        helper = HistoryDatabaseHelper(ApplicationProvider.getApplicationContext());
    }

    @After
    fun tearDown()
    {
        helper.close();
    }

    private fun assertDatabaseOpened(database: SQLiteDatabase) {
        assert(database.isOpen)
    }

    private fun assertInitialDB(database: SQLiteDatabase, helper: HistoryDatabaseHelper) {
        assertDatabaseOpened(database)
        assert(helper.databaseName.equals("SneakerHunterHistory"))
    }

    @Test
    fun testInitialGetReadableDatabase() {
        val database = helper.readableDatabase
        assertInitialDB(database, helper)
        database.close()
    }

    @Test
    fun testAddingNewElementToHistory() {
        val database = helper.writableDatabase

        val shoeValues = ContentValues()
        shoeValues.put("SNEAKER_KEY", 101)

        database.insertOrThrow("HISTORY", null, shoeValues)

        val cursor = database.query(
                "HISTORY", arrayOf("_id", "SNEAKER_KEY"), null,
                null, null, null, "_id DESC"
        )

        cursor.moveToNext()
        assertEquals(101, cursor.getInt(1))

        database.close()
    }

    @Test
    fun testAddingNewElementToFavorites() {
        val database = helper.writableDatabase

        val shoeValues = ContentValues()
        shoeValues.put("SNEAKER_KEY", 101)

        database.insertOrThrow("FAVORITES", null, shoeValues)

        val cursor = database.query(
                "FAVORITES", arrayOf("_id", "SNEAKER_KEY"), null,
                null, null, null, "_id DESC"
        )

        cursor.moveToNext()
        assertEquals(101, cursor.getInt(1))

        database.close()
    }

    @Test
    fun testOverflowingHistoryDatabase() {
        val database = helper.writableDatabase

        val shoesValues = arrayListOf<ContentValues>()
        for (i in 0..23) {
            val shoeValues = ContentValues()
            shoeValues.put("SNEAKER_KEY", i)

            shoesValues.add(shoeValues)
        }

        for (i in shoesValues.indices) {
            database.insertOrThrow("HISTORY", null, shoesValues[i])
        }

        val cursor = database.query(
                "HISTORY", arrayOf("_id", "SNEAKER_KEY"), null,
                null, null, null, "_id DESC"
        )

        assertEquals(20, cursor.count)

        database.close()
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {
    lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .start()
                .pause()
                .resume()
                .get()
    }

    @Test
    fun testMainActivityInit() {
        assertNotNull(activity.applicationContext)
    }
}
