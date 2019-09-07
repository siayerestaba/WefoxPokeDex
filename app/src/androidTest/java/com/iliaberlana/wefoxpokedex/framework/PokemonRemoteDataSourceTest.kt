package com.iliaberlana.wefoxpokedex.framework

import androidx.test.core.app.ApplicationProvider
import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.wefoxpokedex.TestApplication
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError
import com.iliaberlana.wefoxpokedex.framework.remote.NetworkFactory
import com.iliaberlana.wefoxpokedex.framework.remote.PokemonRemoteDataSource
import com.iliaberlana.wefoxpokedex.framework.remote.model.PokemonRemote
import com.iliaberlana.wefoxpokedex.framework.remote.model.Sprites
import com.iliaberlana.wefoxpokedex.framework.remote.model.Type
import com.iliaberlana.wefoxpokedex.framework.remote.model.Types
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class PokemonRemoteDataSourceTest {
    private val mockWebServer = MockWebServer()
    private lateinit var pokemonRemoteDataSource: PokemonRemoteDataSource

    @Before
    fun setUp() {
        mockWebServer.start(8080)

        val httpUrl = mockWebServer.url("/")
        val testApp = ApplicationProvider.getApplicationContext() as TestApplication
        testApp.setApiUrl(httpUrl.toString())

        pokemonRemoteDataSource = PokemonRemoteDataSource(NetworkFactory(), httpUrl.toString())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun should_return_throw_when_response_is_not_200ok() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
        var domainError: DomainError = DomainError.UnknownException

        try {
            pokemonRemoteDataSource.getPokemon(944)
        } catch (error: DomainError) {
            domainError = error
        }

        domainError.shouldBe(DomainError.NoPokemonFound)
    }

    @Test
    fun should_result_a_pokemon() = runBlocking {
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

        val actual = pokemonRemoteDataSource.getPokemon(1)

        actual.shouldNotBeNull()

        val expected = PokemonRemote(
            1,
            1,
            "bulbasaur",
            69,
            7,
            64,
            Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
            listOf(Types(Type("poison")), Types(Type("grass")))
        )

        assertThat(expected).isEqualToWithGivenProperties(
            actual,
            PokemonRemote::id,
            PokemonRemote::order,
            PokemonRemote::name,
            PokemonRemote::weight,
            PokemonRemote::height,
            PokemonRemote::sprites,
            PokemonRemote::base_experience,
            PokemonRemote::types
        )
    }

}