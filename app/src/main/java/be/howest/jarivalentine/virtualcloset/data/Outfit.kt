package be.howest.jarivalentine.virtualcloset.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outfit")
data class Outfit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val label: String,
    val imageUri: String
)

@Entity(tableName = "outfit_item", primaryKeys = ["outfitId", "itemId"])
data class OutfitItem(
    val outfitId: Int,
    val itemId: Int
)
