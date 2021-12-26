package fr.northborders.rickandmorty.data.paging

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import fr.northborders.rickandmorty.data.local.MainDatabase
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.PageKey
import fr.northborders.rickandmorty.data.remote.RickAndMortyService

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(val service: RickAndMortyService, val db: MainDatabase): RemoteMediator<Int, Character>() {

    private val charactersDao = db.charactersDao()
    private val keyDao = db.pageKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val remoteKey: PageKey? = db.withTransaction {
                        if (lastItem?.id != null) {
                            keyDao.getNextPageKey(lastItem.id)
                        } else null
                    }

                    if (remoteKey?.nextPageUrl == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    val uri = Uri.parse(remoteKey.nextPageUrl)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextPageQuery?.toInt()
                }
            }

            val response = service.getCharacters(loadKey ?: 1)
            val resBody = response.body()
            val pageInfo = resBody?.pageInfo
            val characters = resBody?.results

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    charactersDao.clearCharacters()
                    keyDao.clearAll()
                }
                characters?.forEach {
                    it.page = loadKey
                    keyDao.insertOrReplace(PageKey(it.id, pageInfo?.next))
                }
                characters?.let { charactersDao.insertAll(it) }
            }

            MediatorResult.Success(
                endOfPaginationReached = resBody?.pageInfo?.next == null
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}