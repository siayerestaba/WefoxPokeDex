package com.iliaberlana.wefoxpokedex.framework.remote

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.types.shouldBeNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class NetworkFactoryTest {
    private val mockWebServer = MockWebServer()
    private val networkFactory = NetworkFactory()
    private lateinit var pokemonClient: PokemonClient

    @Before
    fun setUp() {
        mockWebServer.start()

        val httpUrl = mockWebServer.url("/")
        pokemonClient = networkFactory.createApi(PokemonClient::class.java, httpUrl.toString())
    }

    @Test
    fun `should have all params`() = runBlocking {
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody(
                "{\n" +
                        "\t\"base_experience\": 64,\n" +
                        "\t\"height\": 7,\n" +
                        "\t\"id\": 1,\n" +
                        "\t\"name\": \"bulbasaur\",\n" +
                        "\t\"order\": 1,\n" +
                        "\t\"sprites\": {\n" +
                        "\t\t\"front_default\": \"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png\"\n" +
                        "\t},\n" +
                        "\t\"types\": [{\n" +
                        "\t\t\t\"slot\": 2,\n" +
                        "\t\t\t\"type\": {\n" +
                        "\t\t\t\t\"name\": \"poison\",\n" +
                        "\t\t\t\t\"url\": \"https://pokeapi.co/api/v2/type/4/\"\n" +
                        "\t\t\t}\n" +
                        "\t\t},\n" +
                        "\t\t{\n" +
                        "\t\t\t\"slot\": 1,\n" +
                        "\t\t\t\"type\": {\n" +
                        "\t\t\t\t\"name\": \"grass\",\n" +
                        "\t\t\t\t\"url\": \"https://pokeapi.co/api/v2/type/12/\"\n" +
                        "\t\t\t}\n" +
                        "\t\t}\n" +
                        "\t],\n" +
                        "\t\"weight\": 69\n" +
                        "}"
            )

        mockWebServer.enqueue(response)

        val actual = pokemonClient.getPokemon(1)

        assertThat(actual.id).isEqualTo(1)
        assertThat(actual.name).isEqualTo("bulbasaur")
        assertThat(actual.height).isEqualTo(7)
        assertThat(actual.weight).isEqualTo(69)
        assertThat(actual.base_experience).isEqualTo(64)
        assertThat(actual.sprites.front_default).isEqualTo("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
        assert(actual.types.size == 2)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}

