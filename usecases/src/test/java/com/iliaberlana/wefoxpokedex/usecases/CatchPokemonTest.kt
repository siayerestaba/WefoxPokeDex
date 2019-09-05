package com.iliaberlana.wefoxpokedex.usecases

import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class CatchPokemonTest {

    @Test
    fun `call PokemonRepository when execute CatchPokemon with same parameters`() = runBlocking {
        val pokemon = Pokemon(1, 1, "bulbasaur", Date(), 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

        val pokemonRepository = mockk<PokemonRepository>(relaxed = true)
        val catchPokemon = CatchPokemon(pokemonRepository)

        catchPokemon(pokemon)

        coVerify { pokemonRepository.catchPokemon(pokemon) }
    }
}