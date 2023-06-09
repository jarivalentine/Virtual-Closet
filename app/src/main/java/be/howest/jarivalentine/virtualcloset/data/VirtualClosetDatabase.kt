package be.howest.jarivalentine.virtualcloset.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class, Outfit::class, OutfitItem::class], version = 4, exportSchema = false)
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
