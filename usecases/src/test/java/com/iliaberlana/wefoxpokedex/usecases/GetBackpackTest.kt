package com.iliaberlana.wefoxpokedex.usecases

import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetBackpackTest {

    @Test
    fun `call PokemonRepository when execute GetBackpack with same parameters`() = runBlocking {
        val pokemonRepository = mockk<PokemonRepository>(relaxed = true)
        val getBackpack = GetBackpack(pokemonRepository)

        getBackpack()

        coVerify { pokemonRepository.getBackpack() }
    }
}