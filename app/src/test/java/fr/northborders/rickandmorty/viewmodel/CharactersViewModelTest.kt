package fr.northborders.rickandmorty.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.google.common.truth.Truth.assertThat
import fr.northborders.rickandmorty.repo.FakeCharactersRepository
import fr.northborders.rickandmorty.ui.CharactersViewModel
import fr.northborders.rickandmorty.util.DataFactory.Factory.makeDummyList
import fr.northborders.rickandmorty.util.MainCoroutineRule
import fr.northborders.rickandmorty.util.UtilPagingData.collectDataForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class CharactersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CharactersViewModel
    private val dummyList = makeDummyList()

    @Before
    fun setup() {
        viewModel = CharactersViewModel(FakeCharactersRepository(dummyList))
    }

    @Test
    fun `list of characters should be properly parsed`() {
        runBlocking {
            val pagingData = viewModel.charactersFlow
            pagingData.collect {
                val list = it.collectDataForTest()
                assertThat(list).isNotEmpty()
                assertThat(list).hasSize(dummyList.size)
                assertThat(list).isEqualTo(dummyList)
            }
        }
    }
}