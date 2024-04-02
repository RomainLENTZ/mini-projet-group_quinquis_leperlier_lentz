package com.leperlier.quinquis.lentz.imdb.ui.menu.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    val title: String = "About"
    val description: String = "Ce projet a été réalisé par : \n" + "Lentz Romain, Leperlier Lucas et Quinquis Yohan. Dans le cadre du cours de développement Android de l'école des Gobelins."

    val explanation: String = "Ce projet utilise principalement l'api de 'The Movie database' afin d'accéder aux informations. L'app se sert de Glide afin de charger les images des films, également ......."

    val linkedInRomain: String = "https://www.linkedin.com/in/romain-lentz-645448200/"
    val linkedInLucas: String = "https://www.linkedin.com/in/lucasleperlier/"
    val linkedInYohan: String = "https://www.linkedin.com/in/yohan-quinquis-a01260205/"

}