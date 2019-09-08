package com.iliaberlana.wefoxpokedex.framework.remote

import com.iliaberlana.wefoxpokedex.framework.remote.model.PokemonRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonClient {
    @GET("/api/v2/pokemon/{pokemonId}")
    suspend fun getPokemon(
        @Path("pokemonId") characterId: Int
    ): PokemonRemote
}