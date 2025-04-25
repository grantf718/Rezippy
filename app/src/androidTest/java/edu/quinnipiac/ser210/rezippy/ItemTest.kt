package edu.quinnipiac.ser210.rezippy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import edu.quinnipiac.ser210.rezippy.data.FavoritesDatabase
import edu.quinnipiac.ser210.rezippy.data.Item
import edu.quinnipiac.ser210.rezippy.data.ItemDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ItemTest {
    private lateinit var database: FavoritesDatabase
    private lateinit var dao: ItemDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoritesDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.itemDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertAndDelete() = runTest {
        val item = Item(id = 1)
        dao.insert(item)

        val retrieved = dao.getItemById(1)
        assertNotNull(retrieved)
        assertEquals(1, retrieved?.id)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllItems() = runTest {
        val item1 = Item(1)
        val item2 = Item(2)
        dao.insert(item1)
        dao.insert(item2)

        val items = dao.getAllItems()
        assertEquals(2, items.size)
        assertEquals(1, items[0]?.id)
        assertEquals(2, items[1]?.id)
    }

    @Test
    @Throws(Exception::class)
    fun testGetItemById() = runTest {
        val item = Item(id = 1)
        dao.insert(item)

        val retrievedItem = dao.getItemById(1)
        assertNotNull(retrievedItem)
        assertEquals(1, retrievedItem?.id)

        // Test non-existing item case
        val nonExistentItem = dao.getItemById(99)
        assertNull(nonExistentItem)
    }
}