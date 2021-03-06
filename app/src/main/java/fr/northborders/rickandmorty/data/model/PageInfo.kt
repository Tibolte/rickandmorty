package fr.northborders.rickandmorty.data.model

import com.google.gson.annotations.SerializedName


data class PageInfo(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("next")
    var next: String?,
    @SerializedName("pages")
    var pages: Int?,
    @SerializedName("prev")
    var prev: String?
)