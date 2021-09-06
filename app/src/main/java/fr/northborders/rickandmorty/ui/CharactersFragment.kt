package fr.northborders.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.northborders.rickandmorty.databinding.FragmentCharactersBinding
import fr.northborders.rickandmorty.di.Injectable

class CharactersFragment: Fragment(), Injectable {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCharactersBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }
}