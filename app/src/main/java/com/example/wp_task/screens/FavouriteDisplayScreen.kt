package com.example.wp_task.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wp_task.model.MovieData
import com.example.wp_task.R

@Composable
fun FavouriteDisplayScreen(onEvent: (MovieEvents) -> Unit, movies: List<MovieData>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(movies, key = { movie -> movie._id }) { movie ->
                FavouriteItem(onEvent, title = movie.titleText, id = movie._id)
            }
        }
    }
}

@Composable
fun FavouriteItem(onEvent: (MovieEvents) -> Unit, title: String, id: String) {
    Column() {
        Row(
            Modifier.padding(horizontal = 12.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = title)
            IconButton(onClick = { onEvent(MovieEvents.UnLike(id, title)) }) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.ic_heart_filled),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
        Divider(modifier = Modifier)
    }

}