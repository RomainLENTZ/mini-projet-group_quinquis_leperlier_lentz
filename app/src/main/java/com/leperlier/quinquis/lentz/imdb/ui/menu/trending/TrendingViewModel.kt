package com.leperlier.quinquis.lentz.imdb.ui.menu.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _trendingDayMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val trendingDayMovies: LiveData<List<Movie>> = _trendingDayMovies

    private val _trendingWeekMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val trendingWeekMovies: LiveData<List<Movie>> = _trendingWeekMovies

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    init {
        getDayTrendingMovies()
        getWeekTrendingMovies()
    }

    private fun getDayTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getDayTrendingMovies()) {
                is Result.Succes -> _trendingDayMovies.postValue(result.data)
                is Result.Error -> _error.postValue(result.message)
            }
        }
    }

    private fun getWeekTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getWeekTrendingMovies()) {
                is Result.Succes -> _trendingWeekMovies.postValue(result.data)
                is Result.Error -> _error.postValue(result.message)
            }
        }
    }
}