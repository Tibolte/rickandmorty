package fr.northborders.rickandmorty.ui

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharactersRepository): BaseViewModel() {

    private lateinit var _charactersFlow: Flow<PagingData<Character>>
    val charactersFlow: Flow<PagingData<Character>>
        get() = _charactersFlow

    init {
        getAllCharacters()
    }

    @ExperimentalPagingApi
    private fun getAllCharacters() = launchPagingAsync({
        repository.characters.cachedIn(viewModelScope)
    }, {
        _charactersFlow = it
    })
}