package com.leperlier.quinquis.lentz.imdb.ui.menu.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrendingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is trending Fragment"
    }
    val text: LiveData<String> = _text
}