package edu.quinnipiac.ser210.rezippy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
)