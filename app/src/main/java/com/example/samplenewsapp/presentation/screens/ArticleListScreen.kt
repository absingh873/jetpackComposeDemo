package com.example.samplenewsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.samplenewsapp.presentation.components.ArticleItem
import com.example.samplenewsapp.presentation.navigation.ArticleNavigationItem
import com.example.samplenewsapp.presentation.viewmodels.ArticleViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleListScreen(navHostController: NavHostController, viewModel: ArticleViewModel = hiltViewModel()) {

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Article List")} ) }) {

        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            val result = viewModel.articleListStateHolder.value

            if (result.isLoading){
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.testTag("progress"))
                }
            }

            if (result.error.isNotBlank()){
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Text(text = result.error)
                }
            }


            if (result.data!=null) {
                LazyColumn(modifier = Modifier.testTag("article_list")) {
                    items(result.data) {
                        ArticleItem(it) {
                            navHostController.navigate(ArticleNavigationItem.ArticleDetail.route + "/$it")
                        }
                    }
                }
            }
        }

    }
}


 
