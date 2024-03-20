package com.example.samplenewsapp.data.local.database

import com.example.samplenewsapp.data.local.entity.Article
import kotlinx.coroutines.flow.Flow

class NewsDatabaseService constructor(private val newsDatabase: NewsDatabase) : DatabaseService {
    override fun getArticles(): Flow<List<Article>> {
        return newsDatabase.articleDao().getAll()
    }

    override fun deleteAllInsertAll(articles: List<Article>) {
        return newsDatabase.articleDao().deleteAllInsertAll(articles)
    }

}