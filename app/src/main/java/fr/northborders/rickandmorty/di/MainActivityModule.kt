package fr.northborders.rickandmorty.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.northborders.rickandmorty.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}