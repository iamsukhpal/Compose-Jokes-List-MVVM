package com.example.jokes.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokes.domain.common.BaseResult
import com.example.jokes.domain.usecase.FetchJokeUseCase
import com.example.jokes.presentation.model.JokeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(private val jokeUseCase: FetchJokeUseCase) : ViewModel() {

    var jokeList = mutableStateListOf<JokeData>()
    val jokeState = MutableStateFlow<JokeState>(JokeState.START)

    fun getJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            jokeUseCase.invoke()
                .onStart { jokeState.emit(JokeState.LOADING) }
                .catch { e ->
                    jokeState.emit(JokeState.FAILURE(e.localizedMessage ?: "Something went wrong!"))
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            result.data?.let {
                                jokeList.add(it.toData())
                            }
                            jokeState.emit(JokeState.SUCCESS)
                        }
                        is BaseResult.Error -> {
                            // 0 means no internet connection
                            if (result.err.code != 0) {
                                val msg = "${result.err.message} [${result.err.code}]"
                                jokeState.emit(JokeState.FAILURE(msg))
                            }

                        }
                        else -> {}
                    }
                }
        }
    }
}