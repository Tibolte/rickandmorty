package fr.northborders.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import fr.northborders.rickandmorty.databinding.ActivityMainBinding
import fr.northborders.rickandmorty.ui.RickAndMortyFragmentFactory
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var rickAndMortyFragmentFactory: RickAndMortyFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragmentFactory = rickAndMortyFragmentFactory
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }
}