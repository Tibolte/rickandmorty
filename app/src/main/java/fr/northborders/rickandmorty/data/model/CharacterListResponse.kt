package fr.northborders.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    @SerializedName("info")
    var info: Info?,
    @SerializedName("results")
    var results: List<Character>
)