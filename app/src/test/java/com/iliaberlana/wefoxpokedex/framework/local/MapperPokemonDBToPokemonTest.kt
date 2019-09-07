package com.iliaberlana.wefoxpokedex.framework.local

import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity
import org.junit.Test
import java.util.*

class MapperPokemonDBToPokemonTest {

    @Test
    fun `should return Pokemon from PokemonDbEntity with same values`() {
        val created = Date()

        val expected =
            Pokemon(1, 1, "bulbasaur", created, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

        val pokemonDB =
            PokemonDbEntity(1, 1, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

        val actual = pokemonDB.toDomain()

        assertThat(expected).isEqualToWithGivenProperties(
            actual,
            Pokemon::id,
            Pokemon::order,
            Pokemon::name,
            Pokemon::weight,
            Pokemon::height,
            Pokemon::imageUrl,
            Pokemon::experience,
            Pokemon::typesPokemon
        )
    }

    @Test
    fun `should return PokemonDbEntity from Pokemon with same values`() {
        val created = Date()

        val expected = PokemonDbEntity(1, 1, "bulbasaur", 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

        val pokemon =
            Pokemon(1, 1, "bulbasaur", created, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

        val actual = PokemonDbEntity.fromDomain(pokemon)

        assertThat(expected).isEqualToWithGivenProperties(
            actual,
            PokemonDbEntity::id,
            PokemonDbEntity::order,
            PokemonDbEntity::name,
            PokemonDbEntity::weight,
            PokemonDbEntity::height,
            PokemonDbEntity::imageUrl,
            PokemonDbEntity::experience,
            PokemonDbEntity::typesPokemon
        )
    }

}