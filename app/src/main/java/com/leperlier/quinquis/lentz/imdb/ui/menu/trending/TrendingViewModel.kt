package com.leperlier.quinquis.lentz.imdb.ui.menu.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leperlier.quinquis.lentz.imdb.data.Movie
import com.leperlier.quinquis.lentz.imdb.data.Serie
import com.leperlier.quinquis.lentz.imdb.repository.MovieRepository
import com.leperlier.quinquis.lentz.imdb.repository.SerieRepository
import kotlinx.coroutines.Dispatchers
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val movieRepository: MovieRepository, private val serieRepository: SerieRepository) : ViewModel() {

    private val _trendingDayMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val trendingDayMovies: LiveData<List<Movie>> = _trendingDayMovies

    private val _trendingWeekMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val trendingWeekMovies: LiveData<List<Movie>> = _trendingWeekMovies

    private val _trendingDaySeries: MutableLiveData<List<Serie>> = MutableLiveData()
    val trendingDaySeries: LiveData<List<Serie>> = _trendingDaySeries

    private val _trendingWeekSeries: MutableLiveData<List<Serie>> = MutableLiveData()
    val trendingWeekSeries: LiveData<List<Serie>> = _trendingWeekSeries

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    init {
        getDayTrendingMovies()
        getWeekTrendingMovies()

        getDayTrendingSeries()
        getWeekTrendingSeries()
    }

    private fun getDayTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = movieRepository.getDayTrendingMovies()) {
                is Result.Succes -> _trendingDayMovies.postValue(result.data)
                is Result.Error -> _error.postValue(result.message)
            }
        }
    }

    private fun getWeekTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = movieRepository.getWeekTrendingMovies()) {
                is Result.Succes -> _trendingWeekMovies.postValue(result.data)
                is Result.Error -> _error.postValue(result.message)
            }
        }
    }

    private fun getDayTrendingSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = serieRepository.getDayTrendingSeries()) {
                is Result.Succes -> _trendingDaySeries.postValue(result.data)
                is Result.Error -> _error.postValue(result.message)
            }
        }
    }

    private fun getWeekTrendingSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = serieRepository.getWeekTrendingSeries()) {
                is Result.Succes -> _trendingWeekSeries.postValue(result.data)
                is Result.Error -> _error.postValue(result.message)
            }
        }
    }
}