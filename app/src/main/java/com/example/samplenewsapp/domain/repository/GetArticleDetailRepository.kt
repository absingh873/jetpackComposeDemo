package com.example.samplenewsapp.domain.repository

import com.example.samplenewsapp.domain.model.detail.ArticleDetail

interface GetArticleDetailRepository {
    suspend fun getArticleDetail(articleApiKey:String):ArticleDetail
}