package com.example.jokes.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jokes.R
import com.example.jokes.presentation.model.JokeData
import com.example.jokes.presentation.theme.JokesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokesTheme {
                val jokeViewModel = hiltViewModel<JokeViewModel>()
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by jokeViewModel.jokeState.collectAsState()
                    LaunchedEffect(Unit) {
                        jokeViewModel.getJokes()
                    }
                    state.let { _state ->
                        when (_state) {
                            JokeState.START -> {
                            }
                            JokeState.LOADING -> {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    CircularProgressIndicator(color = colorResource(id = R.color.purple_700))
                                }
                            }
                            is JokeState.FAILURE -> {
                                val message = _state.message
                                LaunchedEffect(key1 = message) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }

                            JokeState.SUCCESS -> {
                                JokeList(jokeList = jokeViewModel.jokeList)
                            }
                        }
                    }
                }
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JokesTheme {
        Greeting("Android")
    }
}*/

@Composable
fun JokeList(jokeList: List<JokeData>) {
    LazyColumn {
        items(jokeList, key = { it.joke }) { item ->
            JokeItem(jokeData = item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JokeItem() {
    val joke = JokeData(
        "Joke .. hahahahahah"
    )
    JokeItem(jokeData = joke)
}

@Composable
fun JokeItem(jokeData: JokeData) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = jokeData.joke,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}