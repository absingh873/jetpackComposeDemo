package com.example.samplenewsapp.domain.repository

import com.example.samplenewsapp.domain.model.list.Article

interface GetArticleRepository {
    suspend fun getArticle(articlePathKey: String): List<Article>
}