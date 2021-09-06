package fr.northborders.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import fr.northborders.rickandmorty.data.Result
import fr.northborders.rickandmorty.databinding.FragmentCharactersBinding
import fr.northborders.rickandmorty.di.Injectable
import fr.northborders.rickandmorty.di.injectViewModel
import timber.log.Timber
import javax.inject.Inject

class CharactersFragment: Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        val binding = FragmentCharactersBinding.inflate(inflater, container, false)

        //(activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val adapter = CharactersAdapter()
        binding.recyclerView.adapter = adapter


        subscribeUI(binding, adapter)

        return binding.root
    }

    private fun subscribeUI(binding: FragmentCharactersBinding, adapter: CharactersAdapter) {
        viewModel.characters.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    //binding.progressBar.hide()
                    result.data?.let { adapter.submitList(it) }
                }
                Result.Status.LOADING -> {
                    Timber.d("loading")
                }
                Result.Status.ERROR -> {
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}