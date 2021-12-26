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
import fr.northborders.rickandmorty.data.remote.RickAndMortyRemoteDataSource
import fr.northborders.rickandmorty.data.remote.RickAndMortyService
import fr.northborders.rickandmorty.data.resultLiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject constructor(private val db: MainDatabase,
                                                private val service: RickAndMortyService) {
//    val characters = resultLiveData(
//        databaseQuery = { dao.getCharacters() },
//        networkCall = { remoteSource.fetchData() },
//        saveCallResult = { dao.insertAll(it.results) }
//    )

    @ExperimentalPagingApi
    val characters: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
        remoteMediator = CharactersRemoteMediator(service, db)
    ) {
        db.charactersDao().pagingSource()
    }.flow
}