package com.iliaberlana.wefoxpokedex.framework

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.wefoxpokedex.framework.local.PokemonDao
import com.iliaberlana.wefoxpokedex.framework.local.PokemonDatabase
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity
import io.kotlintest.matchers.types.shouldBeNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PokemonDaoTest {

    private lateinit var pokemonDatabase: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao

    @Before
    fun before() {
        pokemonDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), PokemonDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        pokemonDao = pokemonDatabase.pokemonDao()
    }

    @After
    fun after() {
        pokemonDatabase.close()
    }

    @Test
    fun save_in_database_when_execute_save() = runBlocking {
        val firstList = pokemonDatabase.pokemonDao().getAll()
        assertTrue(firstList.isEmpty())

        val recipe = PokemonDbEntity(1, 1, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

        pokemonDatabase.pokemonDao().insert(recipe)

        val list = pokemonDatabase.pokemonDao().getAll()
        assertk.assertThat(recipe)
            .isEqualToWithGivenProperties(list[0], PokemonDbEntity::id, PokemonDbEntity::order, PokemonDbEntity::name, PokemonDbEntity::weight, PokemonDbEntity::height, PokemonDbEntity::imageUrl, PokemonDbEntity::experience, PokemonDbEntity::typesPokemon)
    }

    @Test
    fun get_pokemon_with_id() = runBlocking {
        val firstList = pokemonDatabase.pokemonDao().getAll()
        assertTrue(firstList.isEmpty())

        val recipe = PokemonDbEntity(1, 1, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

        pokemonDatabase.pokemonDao().insert(recipe)

        val pokemon = pokemonDatabase.pokemonDao().getPokemon(1)
        assertk.assertThat(recipe)
            .isEqualToWithGivenProperties(pokemon!!, PokemonDbEntity::id, PokemonDbEntity::order, PokemonDbEntity::name, PokemonDbEntity::weight, PokemonDbEntity::height, PokemonDbEntity::imageUrl, PokemonDbEntity::experience, PokemonDbEntity::typesPokemon)
    }

    @Test
    fun get_not_exist_pokemon() = runBlocking {
        val pokemon = pokemonDatabase.pokemonDao().getPokemon(1)
        pokemon.shouldBeNull()
    }
}