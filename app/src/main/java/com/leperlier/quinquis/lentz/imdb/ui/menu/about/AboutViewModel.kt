package com.leperlier.quinquis.lentz.imdb.ui.menu.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val aboutText = MutableLiveData<String>().apply {
        value = "Ce projet a été réalisé par : \n" +
                "Lentz Romain : \n" +
                "Leperlier Lucas\n" +
                "Quinquis Yohan  : https://www.linkedin.com/in/yohan-quinquis-a01260205/\n" +
                "Dans le cadre du cours de développement Android des l'école des Gobelins."
    }
    val text: LiveData<String> = aboutText
}