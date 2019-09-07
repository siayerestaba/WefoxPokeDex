package com.iliaberlana.wefoxpokedex.usecases

import arrow.core.Either
import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError

class GetBackpack (
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(orderBy: String): Either<DomainError, List<Pokemon>> = pokemonRepository.getBackpack(orderBy)
}