package com.example.samplenewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.samplenewsapp.ui.HomeScreen
import com.example.samplenewsapp.ui.base.openCustomChromeTab
import com.example.samplenewsapp.ui.country.CountryListRoute
import com.example.samplenewsapp.ui.language.LanguageListRoute
import com.example.samplenewsapp.ui.newsbysources.NewsBySourcesRoute
import com.example.samplenewsapp.ui.newssources.NewsSourcesRoute
import com.example.samplenewsapp.ui.search.SearchScreenRoute
import com.example.samplenewsapp.ui.topheadlines.offline.TopHeadlinesOfflineRoute
import com.example.samplenewsapp.ui.topheadlines.online.TopHeadlinesOnlineRoute
import com.example.samplenewsapp.ui.topheadlines.paging.TopHeadlinesPagingRoute

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.TopHeadlinesOnline.route
        ) {
            TopHeadlinesOnlineRoute(
                navController = navController,
                onNewsClick = {
                    openCustomChromeTab(context, it)
                })
        }

        composable(
            route = Screen.TopHeadlinesOffline.route
        ) {
            TopHeadlinesOfflineRoute(
                navController = navController,
                onNewsClick = {
                    openCustomChromeTab(context, it)
                })
        }

        composable(
            route = Screen.TopHeadlinesPagination.route
        ) {
            TopHeadlinesPagingRoute(
                navController = navController,
                onNewsClick = {
                    openCustomChromeTab(context, it)
                })
        }

        composable(
            route = Screen.NewsSources.route
        ) {
            NewsSourcesRoute(navController = navController)
        }

        composable(
            route = Screen.NewsBySources.route,
            arguments = listOf(
                navArgument("sourceId") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("countryCode") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("languageId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { it ->

            val sourceId = it.arguments?.getString("sourceId").toString()
            val countryCode = it.arguments?.getString("countryCode").toString()
            val languageId = it.arguments?.getString("languageId").toString()

            NewsBySourcesRoute(
                onNewsClick = {
                    openCustomChromeTab(context, it)
                }, sourceId = sourceId, countryCode = countryCode, languageId = languageId
            )
        }

        composable(
            route = Screen.Countries.route
        ) {
            CountryListRoute(navController = navController)
        }

        composable(
            route = Screen.Languages.route
        ) {
            LanguageListRoute(navController = navController)

        }

        composable(
            route = Screen.SearchNews.route
        ) {
            SearchScreenRoute(
                navController = navController,
                onNewsClick = {
                    openCustomChromeTab(context, it)
                })
        }
    }
}