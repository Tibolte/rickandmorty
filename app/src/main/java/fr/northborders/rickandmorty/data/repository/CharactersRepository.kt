package fr.northborders.rickandmorty.data.repository

import fr.northborders.rickandmorty.data.local.CharactersDao
import fr.northborders.rickandmorty.data.remote.RickAndMortyRemoteDataSource
import fr.northborders.rickandmorty.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject constructor(private val dao: CharactersDao,
                                                private val remoteSource: RickAndMortyRemoteDataSource) {
    val characters = resultLiveData(
        databaseQuery = { dao.getCharacters() },
        networkCall = { remoteSource.fetchData() },
        saveCallResult = { dao.insertAll(it.results) }
    )
}