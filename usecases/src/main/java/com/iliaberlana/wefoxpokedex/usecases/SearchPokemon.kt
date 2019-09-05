package com.iliaberlana.wefoxpokedex.usecases

import arrow.core.Either
import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError

class SearchPokemon (
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Either<DomainError, Pokemon> = pokemonRepository.searchPokemon()
}