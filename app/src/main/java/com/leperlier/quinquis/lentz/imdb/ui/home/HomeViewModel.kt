package com.leperlier.quinquis.lentz.imdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.repository.MovieRepository
import com.leperlier.quinquis.lentz.imdb.repository.SerieRepository
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository, private val serieRepository: SerieRepository) : ViewModel() {

    private val _movieCategories: MutableLiveData<List<Category>> = MutableLiveData()
    val movieCategories: LiveData<List<Category>> get() = _movieCategories

    private val _serieCategories: MutableLiveData<List<Category>> = MutableLiveData()
    val serieCategories: LiveData<List<Category>> get() = _serieCategories

    private val _token: MutableLiveData<Token> = MutableLiveData()
    val token: LiveData<Token>
        get() = _token

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    private val _trendingDayMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val trendingDayMovies: LiveData<List<Movie>> get() = _trendingDayMovies

    private val _trendingWeekMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val trendingWeekMovies: LiveData<List<Movie>> get() = _trendingWeekMovies

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = movieRepository.getToken()) {
                is Result.Succes -> {
                    _token.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getMovieCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = movieRepository.getCategories()) {
                is Result.Succes -> {
                    _movieCategories.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getSerieCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = serieRepository.getCategories()) {
                is Result.Succes -> {
                    _serieCategories.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

}