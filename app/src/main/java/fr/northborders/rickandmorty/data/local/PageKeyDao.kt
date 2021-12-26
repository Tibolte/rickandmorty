package fr.northborders.rickandmorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.northborders.rickandmorty.data.model.PageKey

@Dao
interface PageKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(pageKey: PageKey)

    @Query("SELECT * FROM pageKey WHERE id LIKE :id")
    fun getNextPageKey(id: Int): PageKey?

    @Query("DELETE FROM pageKey")
    suspend fun clearAll()
}