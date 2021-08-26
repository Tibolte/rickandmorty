package fr.northborders.rickandmorty.data.remote

import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(): Response<CharacterListResponse>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>
}