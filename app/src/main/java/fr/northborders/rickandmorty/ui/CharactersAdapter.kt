package fr.northborders.rickandmorty.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.databinding.ItemCharacterBinding

class CharactersAdapter: ListAdapter<Character, CharactersAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.apply {
            bind(character)
            itemView.tag = character
        }
    }

    class ViewHolder(
        private val binding: ItemCharacterBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character) {
            binding.apply {
                character = item
            }
        }
    }
}

private class DiffCallback: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}