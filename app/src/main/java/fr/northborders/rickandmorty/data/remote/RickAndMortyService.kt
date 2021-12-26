package fr.northborders.rickandmorty.data.remote

import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.data.model.PagedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<PagedResponse>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>
}