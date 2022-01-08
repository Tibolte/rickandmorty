package fr.northborders.rickandmorty.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.paging.ExperimentalPagingApi
import javax.inject.Inject

@ExperimentalPagingApi
class RickAndMortyFragmentFactory @Inject constructor(
    private val charactersAdapter: CharactersAdapter
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CharactersFragment::class.java.name -> CharactersFragment(charactersAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}