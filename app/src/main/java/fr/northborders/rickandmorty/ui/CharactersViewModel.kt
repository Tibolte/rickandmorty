package fr.northborders.rickandmorty.ui

import androidx.lifecycle.ViewModel
import fr.northborders.rickandmorty.data.repository.CharactersRepository
import javax.inject.Inject

class CharactersViewModel @Inject constructor(repository: CharactersRepository): ViewModel() {
    val characters = repository.characters
}