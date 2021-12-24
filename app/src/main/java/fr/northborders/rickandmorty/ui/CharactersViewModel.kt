package fr.northborders.rickandmorty.ui

import androidx.lifecycle.ViewModel
import fr.northborders.rickandmorty.data.repository.CharactersRepository

class CharactersViewModel constructor(repository: CharactersRepository): ViewModel() {
    val characters = repository.characters
}