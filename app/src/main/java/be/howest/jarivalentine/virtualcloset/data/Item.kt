package be.howest.jarivalentine.virtualcloset.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String,
    val brand: String,
    val brandImage: String,
    val available: Boolean,
)
