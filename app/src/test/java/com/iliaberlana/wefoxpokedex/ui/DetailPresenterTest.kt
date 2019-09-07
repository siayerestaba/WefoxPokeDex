package com.iliaberlana.wefoxpokedex.ui

import arrow.core.Either
import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError
import com.iliaberlana.wefoxpokedex.ui.detail.DetailPresenter
import com.iliaberlana.wefoxpokedex.ui.detail.DetailView
import com.iliaberlana.wefoxpokedex.usecases.GetPokemon
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class DetailPresenterTest {
    private val getPokemon = mockk<GetPokemon>()
    private val view = mockk<DetailView>(relaxed = true)
    private lateinit var presenter : DetailPresenter

    @Before
    fun setUp() {
        presenter = DetailPresenter(getPokemon)
        presenter.view = view

        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should call showLoading, execute getPokemon and hideLoading in order when call renderPokemon`() = runBlocking {
        coEvery {
            getPokemon(1)
        } returns Either.right(Pokemon(1, 1, "bulbasaur", Date(), 69, 7, "https://via.placeholder.com/150", 64, listOf("poison, grass")))

        presenter.renderPokemon(1)

        coVerifyOrder {
            presenter.view?.showLoading()
            getPokemon(1)
            presenter.view?.hideLoading()
        }
    }

    @Test
    fun `call showToastMessage with noPokemonFound when call renderPokemon and returns NoPokemonFound`() = runBlocking {
        coEvery {
            getPokemon(1)
        } returns Either.left(DomainError.NoPokemonFound)

        presenter.renderPokemon(1)

        verify(exactly = 1) {
            presenter.view?.showToastMessage(R.string.noPokemonFound)
        }
    }

    @Test
    fun `call showToastMessage with unknownException when call renderPokemon and returns UnknownException`() = runBlocking {
        coEvery {
            getPokemon(1)
        } returns Either.left(DomainError.UnknownException)

        presenter.renderPokemon(1)

        verify(exactly = 1) {
            presenter.view?.showToastMessage(R.string.unknownException)
        }
    }
}