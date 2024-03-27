package com.example.samplenewsapp.data.model.list


import com.google.gson.annotations.SerializedName
data class ArticleListResponseDTO(
    @SerializedName("articles")
    var articles: List<ArticleDTO>,
    @SerializedName("status")
    var status: String, // ok
    @SerializedName("totalResults")
    var totalResults: Int // 288727
){

}