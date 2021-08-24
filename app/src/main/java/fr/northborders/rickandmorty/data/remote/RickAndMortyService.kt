package fr.northborders.rickandmorty.data.remote

import fr.northborders.rickandmorty.data.model.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int?,
        @Query("name") name : String?,
        @Query("status") status: String?,
        @Query("gender") gender : String?
    ) : CharacterListResponse

}