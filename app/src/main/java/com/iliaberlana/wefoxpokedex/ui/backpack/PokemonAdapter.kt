package com.iliaberlana.wefoxpokedex.ui.backpack

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.ui.commons.inflate
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI

class PokemonAdapter(
    private val presenter: BackpackPresenter
): RecyclerView.Adapter<PokemonViewHolder>() {
    private var pokemons: MutableList<PokemonUI> = ArrayList()

    fun addAll(collection: Collection<PokemonUI>) {
        pokemons.addAll(collection)
        notifyDataSetChanged()
    }

    fun clean() {
        pokemons.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(parent.inflate(R.layout.pokemon_item), presenter)

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) = holder.bind(pokemons[position])

    override fun getItemCount(): Int = pokemons.size
}
