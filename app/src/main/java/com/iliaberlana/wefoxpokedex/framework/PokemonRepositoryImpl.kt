package com.iliaberlana.wefoxpokedex.framework

import arrow.core.Either
import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import com.iliaberlana.wefoxpokedex.domain.entities.Ordenation
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError
import com.iliaberlana.wefoxpokedex.framework.local.PokemonDao
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity
import com.iliaberlana.wefoxpokedex.framework.remote.PokemonRemoteDataSource
import com.iliaberlana.wefoxpokedex.ui.commons.logDebug

class PokemonRepositoryImpl(
    private val pokemonDao: PokemonDao,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    override suspend fun catchPokemon(pokemon: Pokemon) {
        pokemonDao.insert(PokemonDbEntity.fromDomain(pokemon))
    }

    override suspend fun getPokemon(pokemonId: Int): Either<DomainError, Pokemon> {
        try {
            val pokemon = pokemonDao.getPokemon(pokemonId)
            if(pokemon == null) return Either.left(DomainError.NoPokemonFound)

            return Either.right(pokemon.toDomain())
        } catch (exception: Exception) {
            javaClass.name.logDebug(exception.message?: "Error in getPokemon")
        }

        return Either.left(DomainError.UnknownException)
    }

    override suspend fun getBackpack(orderBy: String): Either<DomainError, List<Pokemon>> {
        try {
            val listPokemonsCatched = pokemonDao.getAll()

            if(orderBy == Ordenation.ORDER.typeOrdenation) {
                val listSorted = listPokemonsCatched.sortedBy { it.order }
                return Either.right(listSorted.map { it.toDomain() })
            }

            return Either.right(listPokemonsCatched.map { it.toDomain() })
        } catch (exception: Exception) {
            javaClass.name.logDebug(exception.message?: "Error in getBackpack")
        }

        return Either.left(DomainError.UnknownException)
    }

    override suspend fun searchPokemon(): Either<DomainError, Pokemon> {
        try {
            val randomId = (1..1000).random()
            val pokemon = pokemonRemoteDataSource.getPokemon(randomId)

            if(pokemon.name.isEmpty()) return Either.left(DomainError.NoPokemonFound)

            val pokemonInBdd = pokemonDao.getPokemon(pokemon.id)
            if(pokemonInBdd != null) return Either.left(DomainError.PokemonCatched)

            return Either.right(pokemon.toDomain())
        } catch (noInternetConnectionException: DomainError) {
            return Either.left(DomainError.NoInternetConnectionException)
        } catch (exception: Exception) {
            return Either.left(DomainError.UnknownException)
        }
    }
}