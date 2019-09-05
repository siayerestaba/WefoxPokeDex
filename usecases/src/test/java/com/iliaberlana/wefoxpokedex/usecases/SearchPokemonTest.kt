package com.iliaberlana.wefoxpokedex.usecases

import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchPokemonTest {

    @Test
    fun `call PokemonRepository when execute SearchPokemon with same parameters`() = runBlocking {
        val pokemonRepository = mockk<PokemonRepository>(relaxed = true)
        val searchPokemon = SearchPokemon(pokemonRepository)

        searchPokemon()

        coVerify { pokemonRepository.searchPokemon() }
    }
}