package fr.northborders.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import fr.northborders.rickandmorty.Consts
import fr.northborders.rickandmorty.data.local.MainDatabase
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.paging.CharactersRemoteMediator
import fr.northborders.rickandmorty.data.remote.RickAndMortyService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepositoryImpl @Inject constructor(private val db: MainDatabase,
                                                   private val service: RickAndMortyService
): CharactersRepository {
    @ExperimentalPagingApi
    override val characters: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = Consts.NETWORK_PAGE_SIZE, prefetchDistance = Consts.PREFETCH_DISTANCE),
        remoteMediator = CharactersRemoteMediator(service, db)
    ) {
        db.charactersDao().pagingSource()
    }.flow
}