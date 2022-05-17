package com.example.roomdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.roomdata.ui.theme.RoomDataTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppRoomDatabase.getDatabase(this)
        GlobalScope.launch(Dispatchers.IO) {
            Movies.List.forEach { Pelicula ->
                db.peliculaDao().insertPelicula(Pelicula)
            }
            val movies = db.peliculaDao().getPeliculas()
            withContext(Dispatchers.Main) {
                setContent {
                    MyApp {
                        MoviesScreen(movies)
                    }
                }
            }
        }

    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    RoomDataTheme() {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesScreen(movies: List<Pelicula>) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Peliculas")
            })
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            items(items = movies) { movie ->
                CardMovie(movie = movie)
            }
        }
    }
}

@Composable
fun CardMovie(movie: Pelicula) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 5.dp),
        elevation = 5.dp
    ) {
        Column {
            Box {
                AsyncImage(
                    model = movie.imageUrl,
                    contentDescription = movie.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Card(
                    modifier = Modifier.padding(5.dp),
                    elevation = 5.dp
                ) {
                    Text(
                        text = movie.clasificacion,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.9f))
                            .padding(5.dp),
                        color = Color.White
                    )
                }
            }
            Text(
                text = movie.titulo.uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primarySurface)
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}