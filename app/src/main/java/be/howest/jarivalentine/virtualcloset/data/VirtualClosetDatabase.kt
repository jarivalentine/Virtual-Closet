package be.howest.jarivalentine.virtualcloset.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.howest.jarivalentine.virtualcloset.data.item.ItemDao
import be.howest.jarivalentine.virtualcloset.data.outfit.OutfitDao
import be.howest.jarivalentine.virtualcloset.model.Item
import be.howest.jarivalentine.virtualcloset.model.Outfit
import be.howest.jarivalentine.virtualcloset.model.OutfitItem

@Database(entities = [Item::class, Outfit::class, OutfitItem::class], version = 6, exportSchema = false)
abstract class VirtualClosetDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun outfitDao(): OutfitDao

    companion object {
        @Volatile
        private var Instance: VirtualClosetDatabase? = null

        fun getDatabase(context: Context): VirtualClosetDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, VirtualClosetDatabase::class.java, "virtualcloset_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
