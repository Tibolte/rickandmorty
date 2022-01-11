package fr.northborders.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
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
class CharactersFragment @Inject constructor(val charactersAdapter: CharactersAdapter): Fragment() {

    lateinit var viewModel: CharactersViewModel
    lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(requireActivity())[CharactersViewModel::class.java]
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        charactersAdapter.setOnItemClickListener {
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment()
            )
        }

        binding.recyclerView.adapter = charactersAdapter

        subscribeUI()

        Timber.d("RICKANDMORTY", charactersAdapter.itemCount.toString())

        return binding.root
    }

    fun subscribeUI() {
        with(charactersAdapter) {
            binding.swipeRefresh.setOnRefreshListener { refresh() }
            binding.recyclerView.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            with(viewModel) {
                launchOnLifecycleScope {
                    charactersFlow.collectLatest {
                        submitData(it)
                    }
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
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }
}