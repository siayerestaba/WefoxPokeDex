package com.iliaberlana.wefoxpokedex.domain.data

import arrow.core.Either
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError

interface PokemonRepository {
    suspend fun catchPokemon(pokemon: Pokemon)

    suspend fun getPokemon(pokemonId: Int): Either<DomainError, Pokemon>

    suspend fun getBackpack(orderBy: String): Either<DomainError, List<Pokemon>>

    suspend fun searchPokemon(): Either<DomainError,Pokemon>
}