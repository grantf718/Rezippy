package edu.quinnipiac.ser210.rezippy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from items")
    suspend fun getAllItems(): List<Item?>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): Int
}