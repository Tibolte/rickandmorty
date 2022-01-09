package fr.northborders.rickandmorty.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import fr.northborders.rickandmorty.R
import fr.northborders.rickandmorty.data.model.Character
import fr.northborders.rickandmorty.databinding.ItemCharacterBinding

class CharactersAdapter: PagingDataAdapter<Character, CharactersAdapter.ViewHolder>(DiffCallback()) {

    private var itemClickListener: ((Character) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.apply {
            character?.let { bind(it) }
            itemView.tag = character
            itemView.setOnClickListener {
                itemClickListener?.let {
                    it(character!!)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: ((Character) -> Unit)) {
        itemClickListener = listener
    }

    class ViewHolder(
        private val binding: ItemCharacterBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character) {
            binding.apply {
                character = item
            }
            binding.imgCharacter.load(item.image)
            val context = binding.root.context
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_status_circle)
            when (item.status) {
                "Alive" -> {
                    val color = ContextCompat.getColor(context, R.color.green_a700)
                    drawable?.let { DrawableCompat.setTint(it, color) }
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                }
                "Dead" -> {
                    val color = ContextCompat.getColor(context, R.color.red_a700)
                    drawable?.let { DrawableCompat.setTint(it, color) }
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                }
                "unknown" -> {
                    val color = ContextCompat.getColor(context, R.color.gray_700)
                    drawable?.let { DrawableCompat.setTint(it, color) }
                    binding.txtStatus.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                }
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