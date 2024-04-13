package com.leperlier.quinquis.lentz.imdb.ui.serieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leperlier.quinquis.lentz.imdb.data.Serie
import com.leperlier.quinquis.lentz.imdb.data.Token
import com.leperlier.quinquis.lentz.imdb.data.Video
import com.leperlier.quinquis.lentz.imdb.local.entities.FavoriteEntity
import com.leperlier.quinquis.lentz.imdb.repository.FavoriteRepository
import com.leperlier.quinquis.lentz.imdb.repository.SerieRepository
import com.leperlier.quinquis.lentz.imdb.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SerieDetailViewModel @Inject constructor(private val repository: SerieRepository, private val favoriteRepository : FavoriteRepository) : ViewModel() {

    private val _similarSeries: MutableLiveData<List<Serie>> = MutableLiveData()
    val similarSeries: LiveData<List<Serie>> get() = _similarSeries

    private val _token: MutableLiveData<Token> = MutableLiveData()
    val token: LiveData<Token>
        get() = _token

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    private val _trailers = MutableLiveData<List<Video>>()
    val trailers: LiveData<List<Video>> get() = _trailers

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

    fun getSimilarSeries(serieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getSimilarSeries(serieId)) {
                is Result.Succes -> {
                    _similarSeries.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getSerieTrailers(serieId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getSerieTrailers(serieId)) {
                is Result.Succes -> {
                    _trailers.postValue(result.data.filter { it.type == "Trailer" })
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun addFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.addFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.removeFavorite(favorite)
        }
    }

    fun isFavorite(serieId: Int): LiveData<Boolean> {
        return favoriteRepository.isFavorite(serieId.toLong())
    }
}