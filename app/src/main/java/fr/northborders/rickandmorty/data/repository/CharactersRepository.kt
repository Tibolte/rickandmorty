package fr.northborders.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import fr.northborders.rickandmorty.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    @ExperimentalPagingApi
    val characters: Flow<PagingData<Character>>
}