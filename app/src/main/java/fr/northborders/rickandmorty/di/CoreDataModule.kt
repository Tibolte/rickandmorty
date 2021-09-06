package fr.northborders.rickandmorty.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import fr.northborders.rickandmorty.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module to provide core data functionality.
 */
// TODO delete?
@Module
class CoreDataModule {

//    @Provides
//    fun provideLoggingInterceptor() =
//        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
//
//    @Provides
//    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
//        OkHttpClient.Builder().addInterceptor(interceptor)
//            .build()
//
//    @Provides
//    @Singleton
//    fun provideGson(): Gson = Gson()
//
//    @Provides
//    @Singleton
//    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
//        GsonConverterFactory.create(gson)
}