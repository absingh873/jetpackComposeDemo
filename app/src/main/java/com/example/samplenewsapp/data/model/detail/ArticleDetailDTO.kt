package com.example.samplenewsapp.data.model.detail

import com.example.samplenewsapp.data.model.list.SourceDTO
import com.google.gson.annotations.SerializedName

data class ArticleDetailDTO(
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
