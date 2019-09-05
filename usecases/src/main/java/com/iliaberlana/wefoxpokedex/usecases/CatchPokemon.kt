package com.iliaberlana.wefoxpokedex.usecases

import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon

class CatchPokemon (
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(pokemon: Pokemon) = pokemonRepository.catchPokemon(pokemon)
}