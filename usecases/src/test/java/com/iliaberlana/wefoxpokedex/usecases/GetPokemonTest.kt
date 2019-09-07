package com.iliaberlana.wefoxpokedex.usecases

import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetPokemonTest {

    @Test
    fun `call PokemonRepository when execute DetailPokemon with same parameters`() = runBlocking {
        val pokemonRepository = mockk<PokemonRepository>(relaxed = true)
        val detailPokemon = GetPokemon(pokemonRepository)

        detailPokemon(1)

        coVerify { pokemonRepository.getPokemon(1) }
    }
}