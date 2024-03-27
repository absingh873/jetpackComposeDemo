package com.example.samplenewsapp.domain.model.list

data class Article(
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val source: Any,
    val id:String
){}
