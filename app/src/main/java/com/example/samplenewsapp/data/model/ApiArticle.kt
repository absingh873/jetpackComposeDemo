package com.example.samplenewsapp.data.model

import com.google.gson.annotations.SerializedName
import com.example.samplenewsapp.data.local.entity.Article

data class ApiArticle(

    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("source")
    val source: ApiSource
)

fun ApiArticle.toArticleEntity(): Article {
    return Article(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = source.toEntitySource()
    )
}