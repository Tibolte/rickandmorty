package fr.northborders.rickandmorty.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.northborders.rickandmorty.R
import fr.northborders.rickandmorty.data.repository.FakeCharactersRepository
import fr.northborders.rickandmorty.ui.*
import fr.northborders.rickandmorty.util.characterA
import fr.northborders.rickandmorty.util.characterB
import fr.northborders.rickandmorty.util.launchFragmentInHiltContainer
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class CharactersFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: RickAndMortyFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSelectCharacter() {
        val navController = mockkClass(NavController::class)
        val list = listOf(characterA, characterB)

        //every { viewModelMock.charactersFlow } returns flowOf(PagingData.from(list))

        launchFragmentInHiltContainer<CharactersFragment>(
            factory = fragmentFactory,
        ) {
            viewModel = CharactersViewModel(FakeCharactersRepository(list))
            Navigation.setViewNavController(requireView(),navController)
            lifecycleScope.launch {
                charactersAdapter.submitData(PagingData.from(list))
            }
        }

        Thread.sleep(1000) // gives time to pass data to the adapter

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.ViewHolder>(
                0, ViewActions.click()
            )
        )

        verify { navController.navigate(CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment()) }

        // TODO some to see the selected character
    }
}