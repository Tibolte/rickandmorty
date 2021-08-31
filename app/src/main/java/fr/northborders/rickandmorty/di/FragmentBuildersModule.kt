package fr.northborders.rickandmorty.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.northborders.rickandmorty.ui.CharactersFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCharactersFragment(): CharactersFragment
}