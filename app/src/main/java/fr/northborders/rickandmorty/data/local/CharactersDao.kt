package fr.northborders.rickandmorty.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.northborders.rickandmorty.data.model.Character

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Character>)

    @Query("SELECT * FROM characters")
    fun getCharacters(): LiveData<List<Character>>

//    @Query("SELECT * FROM characters")
//    fun getPagedCharacters(): DataSource.Factory<Int, Character>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int?): LiveData<Character>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}