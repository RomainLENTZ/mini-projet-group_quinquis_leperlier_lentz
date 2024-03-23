package com.leperlier.quinquis.lentz.imdb.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.repository.MovieRepository
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> get() = _movies

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

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getMovies()) {
                is Result.Succes -> {
                    _movies.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }
}