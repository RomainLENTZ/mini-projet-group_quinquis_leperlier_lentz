package com.leperlier.quinquis.lentz.imdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leperlier.quinquis.lentz.imdb.data.Category
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Serie
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity
import com.leperlier.quinquis.lentz.imdb.repository.FavoriteRepository
import com.leperlier.quinquis.lentz.imdb.repository.MovieRepository
import com.leperlier.quinquis.lentz.imdb.repository.SerieRepository
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository, private val serieRepository: SerieRepository, private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _movieCategories: MutableLiveData<List<Category>> = MutableLiveData()
    val movieCategories: LiveData<List<Category>> get() = _movieCategories

    private val _serieCategories: MutableLiveData<List<Category>> = MutableLiveData()
    val serieCategories: LiveData<List<Category>> get() = _serieCategories

    private val _favorites: MutableLiveData<List<FavoriteEntity>> = MutableLiveData()
    val favorites: LiveData<List<FavoriteEntity>> get() = _favorites

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

    fun getFavoriteFilmAndSeries(): LiveData<List<FavoriteEntity>>{
        /*viewModelScope.launch(Dispatchers.IO) {

            _favorites.postValue(result.value)
        }*/
        return favoriteRepository.getAllFavorite()
    }


    fun getMovieFromFavoriteId(movieId: Long): LiveData<Movie> {
        val returnResult = MutableLiveData<Movie>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = movieRepository.getMovieById(movieId)) {
                is Result.Succes -> {
                    returnResult.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
        return returnResult
    }

    fun getSerieFromFavoriteId(serieId: Long):  LiveData<Serie>{
        val returnResult = MutableLiveData<Serie>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = serieRepository.getSerieById(serieId)) {
                is Result.Succes -> {
                    returnResult.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
        return returnResult
    }

}