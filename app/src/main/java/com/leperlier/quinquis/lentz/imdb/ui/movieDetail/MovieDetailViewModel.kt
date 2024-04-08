package com.leperlier.quinquis.lentz.imdb.ui.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Provider
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.repository.MovieRepository
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _similarMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val similarMovies: LiveData<List<Movie>> get() = _similarMovies

    private val _providers = MutableLiveData<List<Provider>>()
    val providers: LiveData<List<Provider>> = _providers

    private val _token: MutableLiveData<Token> = MutableLiveData()
    val token: LiveData<Token>
        get() = _token

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getToken()) {
                is Result.Succes -> {
                    _token.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getSimilarMovies(movieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getSimilarMovies(movieId)) {
                is Result.Succes -> {
                    _similarMovies.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getMovieProviders(movieId: Long) {
        viewModelScope.launch {
            when (val result = repository.getMovieProviders(movieId)) {
                is Result.Succes -> {
                    _providers.value = result.data
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

}