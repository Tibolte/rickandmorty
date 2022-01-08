package fr.northborders.rickandmorty.data.repository

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import fr.northborders.rickandmorty.data.local.MainDatabase
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.PageInfo
import fr.northborders.rickandmorty.data.model.PagedResponse
import fr.northborders.rickandmorty.data.paging.CharactersRemoteMediator
import fr.northborders.rickandmorty.data.remote.RickAndMortyService
import fr.northborders.rickandmorty.util.CharactersFactory.Factory.makeCharacter
import fr.northborders.rickandmorty.util.CharactersFactory.Factory.makeDummyList
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediatorTest {

    private val applicationContext = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room
        .inMemoryDatabaseBuilder(applicationContext, MainDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    private val service = mockkClass(RickAndMortyService::class)
    val remoteMediator = CharactersRemoteMediator(service, db)

    @Test
    fun refresh_load_returns_success_result_when_more_data_is_present() = runBlocking {
        val characters = makeDummyList()
        val pagedResponse = PagedResponse(
            pageInfo = PageInfo(
                826,
                "https://rickandmortyapi.com/api/character/?page=2",
                42,
                null
            ),
            results = characters
        )
        coEvery { service.getCharacters(any()) } returns Response.success(pagedResponse)

        val pagingState = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isFalse()
    }

    @Test
    fun refresh_load_success_and_end_of_pagination_when_no_more_data() = runBlocking {
        val characters = makeDummyList()
        val pagedResponse = PagedResponse(
            pageInfo = PageInfo(
                826,
                null,
                42,
                null
            ),
            results = characters
        )

        coEvery { service.getCharacters(any()) } returns Response.success(pagedResponse)

        val pagingState = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isTrue()
    }

    @Test
    fun refresh_load_returns_error_result_when_error_occcurs() = runBlocking {

        coEvery { service.getCharacter(any()) } throws  IOException()

        val pagingState = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
    }

    @Test
    fun prepend_always_succeeds() = runBlocking {
        val pagingState = PagingState<Int, Character>(
            listOf(),
            null,
            PagingConfig(1),
            1
        )
        val result = remoteMediator.load(LoadType.PREPEND, pagingState)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isTrue()
    }

    @Test
    fun append_when_last_item_is_not_null_appends_list() = runBlocking {
        val previousCharacters = listOf(makeCharacter(), makeCharacter())

        db.charactersDao().insertAll(previousCharacters)

        val characters = listOf(makeCharacter(), makeCharacter())
        val expected = previousCharacters + characters

        val pagedResponse = PagedResponse(
            pageInfo = PageInfo(
                826,
                "https://rickandmortyapi.com/api/character/?page=2",
                42,
                null
            ),
            results = characters
        )

        coEvery { service.getCharacters(any()) } returns Response.success(pagedResponse)

        val pagingState = PagingState<Int, Character>(
            listOf(PagingSource.LoadResult.Page(
                data = previousCharacters,
                prevKey = null,
                nextKey = null
            )),
            null,
            PagingConfig(1),
            1
        )
        val result = remoteMediator.load(LoadType.APPEND, pagingState)
        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
    }
}