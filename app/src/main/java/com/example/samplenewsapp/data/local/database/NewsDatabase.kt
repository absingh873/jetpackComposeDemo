package com.example.samplenewsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplenewsapp.data.local.dao.ArticleDao
import com.example.samplenewsapp.data.local.entity.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}