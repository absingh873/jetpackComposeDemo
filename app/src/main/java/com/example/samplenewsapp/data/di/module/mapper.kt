package com.example.samplenewsapp.data.di.module

import com.example.samplenewsapp.data.model.detail.ArticleDetailDTO
import com.example.samplenewsapp.data.model.list.ArticleDTO
import com.example.samplenewsapp.data.model.list.SourceDTO
import com.example.samplenewsapp.domain.model.detail.ArticleDetail
import com.example.samplenewsapp.domain.model.list.Article
import com.example.samplenewsapp.domain.model.list.Source

/**
 * this class provide extension function to do mapping in between
 * null value and provide default value to object if null from server
 */

fun ArticleDTO.toDomain()=ArticleDetail (
            imageUrl = imageUrl?:"",
            source = source.toDomain()?:Source("",""),
            title = title?:"",
            description = description?:"",
            url =url?:""
        )

fun List<ArticleDTO>.toDomain() : List<Article>{
    return map{
        Article(
            imageUrl = it.imageUrl?:"",
            source = it.source?:Source("",""),
            title = it.title?:"",
            description = it.description?:"",
            url =it.url?:"",
            id = "660411672b1b334a633c2717"
        )
    }
}
fun SourceDTO.toDomain()=Source(
    id=id?:"",
    name = name?:""
)
fun Article.toArticleDetail()=ArticleDetail(
    imageUrl = imageUrl?:"",
    source = source?:Source("",""),
    title = title?:"",
    description = description?:"",
    url =url?:""
)
fun ArticleDetailDTO.toDomain()=ArticleDetail(
    imageUrl = imageUrl?:"",
    source = source?:Source("",""),
    title = title?:"",
    description = description?:"",
    url =url?:""
)

