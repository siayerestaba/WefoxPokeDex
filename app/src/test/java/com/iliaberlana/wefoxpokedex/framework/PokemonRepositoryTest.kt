package com.iliaberlana.wefoxpokedex.framework

import com.iliaberlana.wefoxpokedex.domain.entities.Ordenation
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.framework.local.PokemonDao
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity
import com.iliaberlana.wefoxpokedex.framework.remote.PokemonRemoteDataSource
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class PokemonRepositoryTest {
    private val pokemon1 = Pokemon(1, 1, "bulbasaur", Date(), 69, 7, "https://via.placeholder.com/150", 64, listOf("poison", "grass"))
    private val pokemon2 = Pokemon(2, 2, "bulbasaur", Date(), 69, 7, "https://via.placeholder.com/150", 64, listOf("poison", "grass"))
    private val pokemon3 = Pokemon(3, 3, "bulbasaur", Date(), 69, 7, "https://via.placeholder.com/150", 64, listOf("poison", "grass"))
    private val pokemon4 = Pokemon(4, 4, "bulbasaur", Date(), 69, 7, "https://via.placeholder.com/150", 64, listOf("poison", "grass"))
    private val pokemon1DB = PokemonDbEntity(1, 1, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")
    private val pokemon2DB = PokemonDbEntity(2, 2, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")
    private val pokemon3DB = PokemonDbEntity(3, 3, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")
    private val pokemon4DB = PokemonDbEntity(4, 4, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

    private val pokemonDao = mockk<PokemonDao>()
    private val pokemonDataSource = mockk<PokemonRemoteDataSource>()
    private val pokemonRepositoryImpl = PokemonRepositoryImpl(pokemonDao, pokemonDataSource)


    @Test
    fun `return list order By Order`() = runBlocking {
        val expected = listOf(pokemon1, pokemon2, pokemon3, pokemon4)

        coEvery { pokemonDao.getAll() } returns listOf(pokemon4DB, pokemon3DB, pokemon1DB, pokemon2DB)

        val actual = pokemonRepositoryImpl.getBackpack(Ordenation.ORDER.typeOrdenation)

        actual.shouldBeRight(expected)
    }

    @Test
    fun `return list order By date`() = runBlocking {
        val expected = listOf(pokemon4, pokemon3, pokemon2, pokemon1)

        coEvery { pokemonDao.getAll() } returns listOf(pokemon4DB, pokemon3DB, pokemon2DB, pokemon1DB)

        val actual = pokemonRepositoryImpl.getBackpack(Ordenation.CATCHED.typeOrdenation)

        actual.shouldBeRight(expected)
    }

}