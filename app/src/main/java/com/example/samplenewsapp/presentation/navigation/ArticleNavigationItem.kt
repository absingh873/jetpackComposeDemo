package com.example.samplenewsapp.presentation.navigation

sealed class ArticleNavigationItem (val route : String) {

    object ArticleList : ArticleNavigationItem("article_list")

    object ArticleDetail : ArticleNavigationItem("article_detail")

}