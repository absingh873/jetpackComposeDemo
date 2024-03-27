package com.example.samplenewsapp.data.network

import com.example.samplenewsapp.data.model.detail.ArticleDetailDTO
import com.example.samplenewsapp.data.model.list.ArticleListResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

/*
* THis class will provide method to make api call to news api
* new API is free tool online source for news
* */
@Singleton
interface NetworkService {
    @GET("/v3/b/{pathkey}")
    suspend fun getNewsList(@Path("pathkey")pathkey:String,):ArticleListResponseDTO
    @GET("/v3/b/{pathkey}")
    suspend fun getArticle(@Path("pathkey")pathkey:String,):ArticleDetailDTO
}