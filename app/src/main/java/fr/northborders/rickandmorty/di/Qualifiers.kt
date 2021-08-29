package fr.northborders.rickandmorty.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CharactersAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO