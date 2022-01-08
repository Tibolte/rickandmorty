package fr.northborders.rickandmorty.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCharactersRepository(private val dummyList: List<Character>): CharactersRepository {
    @ExperimentalPagingApi
    override val characters: Flow<PagingData<Character>>
        get() = flowOf(PagingData.from(dummyList))
}