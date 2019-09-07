package com.iliaberlana.wefoxpokedex.framework.remote

import com.iliaberlana.wefoxpokedex.domain.exception.DomainError
import com.iliaberlana.wefoxpokedex.framework.remote.model.PokemonRemote

class PokemonRemoteDataSource(
    private val network : NetworkFactory,
    private val urlBase: String
)
{
    suspend fun getPokemon(pokemonId: Int): PokemonRemote {
        try {
            val pokemonApi = network.createApi(PokemonClient::class.java, urlBase)

            return pokemonApi.getPokemon(pokemonId)
        } catch (error: Error) {
            throw DomainError.NoPokemonFound
        } catch (error: Exception) {
            throw DomainError.NoPokemonFound
        }
    }
}