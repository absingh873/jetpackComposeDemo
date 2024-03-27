package com.example.samplenewsapp.data.model.list

import com.google.gson.annotations.SerializedName

data class ArticleDTO(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("source")
    val source: SourceDTO
)
