package com.iliaberlana.wefoxpokedex.ui.backpack

import com.iliaberlana.wefoxpokedex.ui.commons.BaseView
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI

interface BackpackView: BaseView {
    fun cleanList()

    fun listPokemon(pokemons: List<PokemonUI>)

    fun showPokemon(pokemonUI: PokemonUI)

    fun showPokemonToCatch(pokemonUI: PokemonUI)

    fun showErrorCase(stringId: Int)
    fun hideErrorCase()
}