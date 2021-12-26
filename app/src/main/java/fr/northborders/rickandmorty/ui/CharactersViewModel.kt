package fr.northborders.rickandmorty.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.northborders.rickandmorty.data.repository.CharactersRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(repository: CharactersRepository): ViewModel() {
    val characters = repository.characters
}