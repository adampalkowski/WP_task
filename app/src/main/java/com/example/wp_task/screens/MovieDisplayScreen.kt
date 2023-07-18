package com.example.wp_task.screens

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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.wp_task.R
import com.example.wp_task.model.Movie


@Composable
fun MovieDisplayScreen(movie: Movie, onEvent: (MovieEvents) -> Unit,favourite:Boolean) {
    var favouriteState by rememberSaveable{ mutableStateOf(favourite) }

    //reset the favourite icon on change of the movie and if favourites was removed
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

                if(movie.primaryImage!=null){
                    AsyncImage(modifier = Modifier,model = movie.primaryImage.url, contentDescription = "movie poster")

                }else{
                    Text(
                        text = "Missing poster",
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))
                if(movie.titleText!=null){
                    Text(
                        text = movie.titleText.text,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    )
                }else{
                    Text(
                        text = "Missing title",
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    )
                }




                Spacer(modifier = Modifier.height(12.dp))
                if(movie.releaseYear!=null){
                    Text(
                        text = movie.releaseYear.year.toString(),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                    )
                }else{
                    Text(
                        text = "Missing release year",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    )
                }

            }

            Column() {
                Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
                    IconButton(modifier=Modifier.semantics { contentDescription="Refresh" },onClick = { onEvent(MovieEvents.NewMovie) }) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "get new movie icon"
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if(favouriteState){
                        IconButton(modifier=Modifier.semantics { contentDescription="Favourites" },onClick = { onEvent(MovieEvents.UnLike(id=movie._id, title = movie.titleText.text))
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