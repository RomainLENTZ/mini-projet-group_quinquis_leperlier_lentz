package com.leperlier.quinquis.lentz.imdb.di

import android.content.Context
import androidx.room.Room
import com.gmail.eamosse.idbdata.BuildConfig
import com.leperlier.quinquis.lentz.imdb.api.service.MovieService
import com.leperlier.quinquis.lentz.imdb.api.service.SerieService
import com.leperlier.quinquis.lentz.imdb.local.daos.FavoritesDao
import com.leperlier.quinquis.lentz.imdb.local.daos.TokenDao
import com.leperlier.quinquis.lentz.imdb.local.databases.AppDatabase
import com.leperlier.quinquis.lentz.imdb.local.databases.IdbDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    internal fun provideDatabase(@ApplicationContext appContext: Context): IdbDataBase {
        return Room.databaseBuilder(
            appContext,
            IdbDataBase::class.java, "idb_database.db"
        ).build()
    }

    @Provides
    internal fun providetokenDao(database: IdbDataBase): TokenDao {
        return database.tokenDao()
    }

    @Provides
    @Singleton
    internal fun provideFavoritesDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "favorites_database.db"
        ).build()
    }

    @Provides
    internal fun provideFavoritesDao(database: AppDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    @Provides
    internal fun provideHttpClient(@Named("API_KEY") apiKey: String, dao: TokenDao): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BasicInterceptor(apiKey).requestInterceptor)
            .addInterceptor(
                TokenInterceptor(
                    dao
                ).requestInterceptor
            )
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    internal fun retrofitBuilder(
        @Named("BASE_URL") baseUrl: String,
        httpClient: OkHttpClient
    ): MovieService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(httpClient)
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    internal fun provideSerieService(
        @Named("BASE_URL") baseUrl: String,
        httpClient: OkHttpClient
    ): SerieService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(httpClient)
            .build()
            .create(SerieService::class.java)
    }
}

/**
 * Un intercepteur qui modifie toutes les requêtes HTTP en y ajoutant un token
 * Le token est récupéré dans la BDD
 */
private class TokenInterceptor(
    private val dao: TokenDao
) {

    val requestInterceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder().url(originalHttpUrl.newBuilder().build())
        dao.retrieve()?.token?.let {
            requestBuilder.addHeader("Authorization", it)
        }
        val request = requestBuilder.build()
        return@Interceptor chain.proceed(request)
    }
}

/**
 * Intercepteur qui modifie l'entête de chaque requête
 */
class BasicInterceptor(
    apiKey: String
) {
    val requestInterceptor = Interceptor { chain ->
        val original = chain.request()

        val url = original.url
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .addHeader("Accept-Language", Locale.getDefault().language)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .url(url)

        val request = requestBuilder.build()
        return@Interceptor chain.proceed(request)
    }
}