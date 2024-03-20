package com.example.samplenewsapp.data.local.database

import com.example.samplenewsapp.data.local.entity.Article
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    fun getArticles(): Flow<List<Article>>
    fun deleteAllInsertAll(articles: List<Article>)

}