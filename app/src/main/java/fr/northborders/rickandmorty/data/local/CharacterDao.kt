package fr.northborders.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.northborders.rickandmorty.data.model.CharacterModel

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<CharacterModel>)


    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, CharacterModel>


    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}