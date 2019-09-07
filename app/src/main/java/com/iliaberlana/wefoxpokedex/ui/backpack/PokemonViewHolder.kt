package com.iliaberlana.wefoxpokedex.ui.backpack

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iliaberlana.wefoxpokedex.ui.commons.loadImage
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pokemon_item.view.*

class PokemonViewHolder(
    override val containerView: View,
    private val presenter: BackpackPresenter
) : RecyclerView.ViewHolder(containerView), LayoutContainer
{
    fun bind(pokemon: PokemonUI) {
        containerView.pokemonImage.loadImage(pokemon.imageUrl)
        containerView.pokemonName.text = pokemon.name

        containerView.setOnClickListener {
            presenter.onPokemonClicked(pokemon)
        }
    }
}