package com.example.samplenewsapp.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.samplenewsapp.presentation.screens.ArticleDetailsScreen
import com.example.samplenewsapp.presentation.screens.ArticleListScreen

@Composable
fun ArtcleNavigation(navHostController: NavHostController) {

    NavHost(navController = navHostController,
        startDestination = ArticleNavigationItem.ArticleList.route ){

        composable(ArticleNavigationItem.ArticleList.route){
            ArticleListScreen(navHostController)
        }

        composable(ArticleNavigationItem.ArticleDetail.route+"/{id}"){
            val id = it.arguments?.getString("id")
            Log.d("MovieDetailsScreen",""+id)
            ArticleDetailsScreen()
        }
    }
}