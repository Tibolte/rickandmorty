package fr.northborders.rickandmorty.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
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
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

        launchFragmentInHiltContainer<CharactersFragment>(
            factory = fragmentFactory,
        ) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = CharactersViewModel(FakeCharactersRepository(list))
            subscribeUI()
        }

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.ViewHolder>(
                0, ViewActions.click()
            )
        )

        verify { navController.navigate(CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment()) }

        // TODO some to see the selected character
    }
}