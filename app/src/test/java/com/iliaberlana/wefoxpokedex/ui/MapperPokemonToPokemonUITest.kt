package com.iliaberlana.wefoxpokedex.ui

import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI
import org.junit.Test
import java.util.*

class MapperPokemonToPokemonUITest {

    @Test
    fun `should return Pokemon from PokemonUI with same values`() {
        val created = Date()

        val expected =
            Pokemon(1, 1, "bulbasaur", created, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

        val pokemonUI =
            PokemonUI(1, 1, "bulbasaur", created, 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

        val actual = pokemonUI.toDomain()

        assertThat(expected).isEqualToWithGivenProperties(
            actual,
            Pokemon::id,
            Pokemon::order,
            Pokemon::name,
            Pokemon::created,
            Pokemon::weight,
            Pokemon::height,
            Pokemon::imageUrl,
            Pokemon::experience,
            Pokemon::typesPokemon
        )
    }

    @Test
    fun `should return PokemonUI from Pokemon with same values`() {
        val created = Date()

        val expected =
            PokemonUI(1, 1, "bulbasaur", created, 69, 7, "https://via.placeholder.com/150", 64, "poison, grass")

        val pokemon =
            Pokemon(1, 1, "bulbasaur", created, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass"))

        val actual = PokemonUI.fromDomain(pokemon)

        assertThat(expected).isEqualToWithGivenProperties(
            actual,
            PokemonUI::id,
            PokemonUI::order,
            PokemonUI::name,
            PokemonUI::created,
            PokemonUI::weight,
            PokemonUI::height,
            PokemonUI::imageUrl,
            PokemonUI::experience,
            PokemonUI::typesPokemon
        )
    }
}