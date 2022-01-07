package fr.northborders.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import fr.northborders.rickandmorty.Consts.NETWORK_PAGE_SIZE
import fr.northborders.rickandmorty.Consts.PREFETCH_DISTANCE
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.local.MainDatabase
import fr.northborders.rickandmorty.data.paging.CharactersRemoteMediator
import fr.northborders.rickandmorty.data.remote.RickAndMortyService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface CharactersRepository {
    @ExperimentalPagingApi
    val characters: Flow<PagingData<Character>>
}