package fr.northborders.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class PagedResponse(
    @SerializedName("info")
    var pageInfo: PageInfo?,
    @SerializedName("results")
    var results: List<Character>
)