package fr.northborders.rickandmorty.data.remote

import javax.inject.Inject

class RickAndMortyRemoteDataSource @Inject constructor(private val service: RickAndMortyService): BaseDataSource() {
    suspend fun fetchData() = getResult { service.getCharacters() }
}