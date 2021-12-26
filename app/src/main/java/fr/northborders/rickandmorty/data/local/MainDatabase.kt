package fr.northborders.rickandmorty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.northborders.rickandmorty.Consts
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.OriginTypeConverter
import fr.northborders.rickandmorty.data.model.PageKey

@Database(
    entities = [Character::class, PageKey::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(OriginTypeConverter::class)
abstract class MainDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun pageKeyDao(): PageKeyDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MainDatabase::class.java, Consts.DATABASE_NAME)
                .build()
    }
}