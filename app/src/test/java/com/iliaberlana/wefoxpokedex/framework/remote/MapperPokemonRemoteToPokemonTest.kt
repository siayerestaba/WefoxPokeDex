package com.iliaberlana.wefoxpokedex.framework.remote

import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity
import com.iliaberlana.wefoxpokedex.framework.remote.model.PokemonRemote
import com.iliaberlana.wefoxpokedex.framework.remote.model.Sprites
import com.iliaberlana.wefoxpokedex.framework.remote.model.Type
import com.iliaberlana.wefoxpokedex.framework.remote.model.Types
import org.junit.Test
import java.util.*

class MapperPokemonRemoteToPokemonTest {

    @Test
    fun `should return Pokemon from PokemonRemote with same values`() {
        val created = Date()

        val expected =
            Pokemon(1, 1, "bulbasaur", created, 69, 7, "https://via.placeholder.com/150", 64, listOf("poison", "grass"))

        val pokemonDB = PokemonRemote(
            1,
            1,
            "bulbasaur",
            69,
            7,
            64,
            Sprites("https://via.placeholder.com/150"),
            listOf(Types(Type("poison")), Types(Type("grass")))
        )

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
}