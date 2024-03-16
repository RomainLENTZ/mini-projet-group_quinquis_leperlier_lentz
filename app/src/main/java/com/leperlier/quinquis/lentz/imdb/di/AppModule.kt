package com.gmail.eamosse.imdb.di

import android.content.Context
import com.gmail.eamosse.imdb.ui.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Named ("API_KEY")
    @Provides
    fun provideApiKey() = "fcd5cad08ccf0de3bb0b30bc344faa27"

    @Named ("BASE_URL")
    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

}
/*
val appModule = module {
    // On injecte l'api key en utilisant la propriété named
    // ce qui permet de lui donner un nom (ici API_KEY)
    single(named("API_KEY")) {
        "507a86e6d98ae2b2cd600e594ee02637"
    }

    single(named("BASE_URL")) {
        "https://api.themoviedb.org/3/"
    }

    single(named("APP_PREFS")) {
        androidContext().getSharedPreferences("app_private", Context.MODE_PRIVATE)
    }

    viewModel {
        HomeViewModel(repository = get())
    }
}*/