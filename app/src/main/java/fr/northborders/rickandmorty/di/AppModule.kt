package fr.northborders.rickandmorty.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.northborders.rickandmorty.Consts
import fr.northborders.rickandmorty.data.local.MainDatabase
import fr.northborders.rickandmorty.data.remote.RickAndMortyService
import fr.northborders.rickandmorty.data.repository.CharactersRepositoryImpl
import fr.northborders.rickandmorty.data.repository.CharactersRepository
import fr.northborders.rickandmorty.ui.CharactersAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)

    @Provides
    @Singleton
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Singleton
    @Provides
    fun provideDb(app: Application) = MainDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideCharacterDao(db: MainDatabase) = db.charactersDao()

    @Singleton
    @Provides
    fun providePageKeyDao(db: MainDatabase) = db.pageKeyDao()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient.Builder): Retrofit =
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)

    @Provides
    @Singleton
    fun provideCharactersRepos(db: MainDatabase, service: RickAndMortyService) =
        CharactersRepositoryImpl(db, service) as CharactersRepository

    @Provides
    @Singleton
    fun provideCharactersAdapter() = CharactersAdapter()
}