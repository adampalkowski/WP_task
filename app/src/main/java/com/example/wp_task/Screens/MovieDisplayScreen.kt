package com.example.wp_task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.wp_task.Screens.MovieEvents
import com.example.wp_task.model.Movie
import com.example.wp_task.model.MovieData


@Composable
fun MovieDisplayScreen(movie: Movie, onEvent: (MovieEvents) -> Unit,favourite:Boolean) {
    var favouriteState by rememberSaveable{ mutableStateOf(favourite) }
    LaunchedEffect(movie._id,favourite) {
        // Reset favouriteState when movie changes
        favouriteState = favourite
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                if (movie.primaryImage == null) {
                    Text(text = "Missing poster : id-" + movie.id)
                } else {
                    AsyncImage(modifier = Modifier.heightIn(min=250.dp,max=1000.dp),model = movie.primaryImage.url, contentDescription = "movie poster")
                }

                Spacer(modifier = Modifier.height(24.dp))
                if (movie.titleText == null) {
                    Text(text = "Missing title : id-" + movie.id)
                } else {
                    Text(
                        text = movie.titleText.text,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    )
                }


                Spacer(modifier = Modifier.height(12.dp))
                if (movie.releaseYear == null) {
                    Text(text = "Missing release year : id-" + movie.id)
                } else {
                    Text(
                        text = movie.releaseYear.year.toString(),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                    )
                }
            }

            Column() {
                Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
                    IconButton(onClick = { onEvent(MovieEvents.NewMovie) }) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "get new movie icon"
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if(favouriteState){
                        IconButton(onClick = { onEvent(MovieEvents.UnLike(id=movie._id, title = movie.titleText.text))
                            favouriteState=false
                        }) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = R.drawable.ic_heart_filled),
                                contentDescription = "favourite movie icon ", tint = Color.Red
                            )
                        }

                    }else{
                        IconButton(onClick = { onEvent(MovieEvents.AddToFavourite)
                            favouriteState=true}) {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = R.drawable.ic_heart),
                                contentDescription = "favourite movie icon ", tint = Color.Red
                            )
                        }

                    }

                }
            }
        }
    }

}