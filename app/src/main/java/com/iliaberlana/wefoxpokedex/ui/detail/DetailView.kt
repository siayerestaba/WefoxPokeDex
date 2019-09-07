package com.iliaberlana.wefoxpokedex.ui.detail

import com.iliaberlana.wefoxpokedex.ui.commons.BaseView
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI

interface DetailView : BaseView {
    fun showPokemon(pokemonUI: PokemonUI)

    fun updateActionBar(pokemonName: String)
}