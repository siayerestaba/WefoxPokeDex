package com.iliaberlana.wefoxpokedex.framework

import com.iliaberlana.wefoxpokedex.domain.entities.Ordenation
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError
import com.iliaberlana.wefoxpokedex.framework.local.PokemonDao
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity
import com.iliaberlana.wefoxpokedex.framework.remote.PokemonRemoteDataSource
import com.iliaberlana.wefoxpokedex.framework.remote.model.PokemonRemote
import com.iliaberlana.wefoxpokedex.framework.remote.model.Sprites
import com.iliaberlana.wefoxpokedex.framework.remote.model.Type
import com.iliaberlana.wefoxpokedex.framework.remote.model.Types
import io.kotlintest.assertions.arrow.either.shouldBeLeft
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class PokemonRepositoryTest {
    private val date1 = Date()
    private val pokemon1 =
        Pokemon(1, 1, "bulbasaur", date1, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))
    private val pokemon1DB =
        PokemonDbEntity(1, 1, "bulbasaur", date1, 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

    private val date2 = Date()
    private val pokemon2DB =
        PokemonDbEntity(2, 2, "bulbasaur", date2, 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")
    private val pokemon2 =
        Pokemon(2, 2, "bulbasaur", date2, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

    private val date3 = Date()
    private val pokemon3DB =
        PokemonDbEntity(3, 3, "bulbasaur", date3, 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")
    private val pokemon3 =
        Pokemon(3, 3, "bulbasaur", date3, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

    private val date4 = Date()
    private val pokemon4DB =
        PokemonDbEntity(4, 4, "bulbasaur", date4, 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")
    private val pokemon4 =
        Pokemon(4, 4, "bulbasaur", date4, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

    private val pokemonRemote = PokemonRemote(
        1,
        1,
        "bulbasaur",
        69,
        7,
        64,
        Sprites("https://via.placeholder.com/150"),
        listOf(Types(Type("poison")), Types(Type("grass")))
    )

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

    @Test
    fun `search a pokemon return ERROR when pokemon exist in bdd`() = runBlocking {
        coEvery { pokemonDataSource.getPokemon(any()) } returns pokemonRemote
        coEvery { pokemonDao.getPokemon(1) } returns pokemon1DB

        val actual = pokemonRepositoryImpl.searchPokemon()

        actual.shouldBeLeft(DomainError.PokemonCatched)
    }

    @Test
    fun `search a pokemon return pokemon when pokemon not exist in bdd`() = runBlocking {
        coEvery { pokemonDataSource.getPokemon(any()) } returns pokemonRemote
        coEvery { pokemonDao.getPokemon(1) } returns null

        val actual = pokemonRepositoryImpl.searchPokemon()

        actual.shouldBeRight()
    }

    @Test
    fun `getpokemon return ERROR when pokemon not exist in bdd`() = runBlocking {
        coEvery { pokemonDao.getPokemon(1) } returns null

        val actual = pokemonRepositoryImpl.getPokemon(1)

        actual.shouldBeLeft(DomainError.NoPokemonFound)
    }

    @Test
    fun `getpokemon return pokemon when pokemon exist in bdd`() = runBlocking {
        coEvery { pokemonDao.getPokemon(1) } returns pokemon1DB

        val actual = pokemonRepositoryImpl.getPokemon(1)

        actual.shouldBeRight(pokemon1)
    }
}