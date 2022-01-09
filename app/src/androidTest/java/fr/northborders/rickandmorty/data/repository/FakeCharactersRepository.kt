package fr.northborders.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import fr.northborders.rickandmorty.data.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCharactersRepository(private val dummyList: List<Character>): CharactersRepository {
    @ExperimentalPagingApi
    override val characters: Flow<PagingData<Character>>
        get() = flowOf(PagingData.from(dummyList))
}