package com.example.samplenewsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.samplenewsapp.R
import com.example.samplenewsapp.presentation.viewmodels.ArticleDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleDetailsScreen(articleDetailsViewModel: ArticleDetailsViewModel = hiltViewModel()) {

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Article Details")} ) }) {
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){

            val result = articleDetailsViewModel.articleDetailsStateHolder.value

            if (result.isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.testTag("progress"))
                }
            }

            if (result.error.isNotBlank()){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = result.error)
                }
            }

            result.data?.let {

                Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                    AsyncImage(model = it.imageUrl,
                        placeholder = painterResource(id = R.drawable.def),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(400.dp)
                            .testTag("articleDetailsImage")
                            .clip(RoundedCornerShape(16.dp))

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = it.title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.testTag("articleDetailsTitle"))

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = it.description, style = MaterialTheme.typography.titleMedium, modifier = Modifier.wrapContentHeight().testTag("articleDetailsTagLine"))

                }
            }
        }
    }
}