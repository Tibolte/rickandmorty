package fr.northborders.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.northborders.rickandmorty.data.Result
import fr.northborders.rickandmorty.data.repository.CharactersRepository
import fr.northborders.rickandmorty.databinding.FragmentCharactersBinding
import fr.northborders.rickandmorty.util.PagingLoadStateAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import timber.log.Timber
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class CharactersFragment: Fragment() {

    //@Inject
    //lateinit var repository: CharactersRepository

    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCharactersBinding.inflate(inflater, container, false)

        val adapter = CharactersAdapter()
        binding.recyclerView.adapter = adapter

        subscribeUI(binding, adapter)

        return binding.root
    }

    private fun subscribeUI(binding: FragmentCharactersBinding, adapter: CharactersAdapter) {
        with(adapter) {
            binding.swipeRefresh.setOnRefreshListener { refresh() }
            binding.recyclerView.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            with(viewModel) {
                launchOnLifecycleScope {
                    charactersFlow.collectLatest { submitData(it) }
                }
                launchOnLifecycleScope {
                    loadStateFlow.collectLatest {
                        binding.swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                    }
                }
                launchOnLifecycleScope {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { binding.recyclerView.scrollToPosition(0) }
                }
            }
        }
//        viewModel.characters.observe(viewLifecycleOwner, Observer { result ->
//            when (result.status) {
//                Result.Status.SUCCESS -> {
//                    binding.progress.hide()
//                    result.data?.let { adapter.submitList(it) }
//                }
//                Result.Status.LOADING -> {
//                    binding.progress.show()
//                    Timber.d("loading")
//                }
//                Result.Status.ERROR -> {
//                    binding.progress.hide()
//                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
//                }
//            }
//        })
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }
}