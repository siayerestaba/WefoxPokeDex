package com.iliaberlana.wefoxpokedex.ui

import arrow.core.Either
import com.iliaberlana.wefoxpokedex.domain.entities.Ordenation
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import com.iliaberlana.wefoxpokedex.ui.backpack.BackpackPresenter
import com.iliaberlana.wefoxpokedex.ui.backpack.BackpackView
import com.iliaberlana.wefoxpokedex.usecases.CatchPokemon
import com.iliaberlana.wefoxpokedex.usecases.GetBackpack
import com.iliaberlana.wefoxpokedex.usecases.SearchPokemon
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class BackpackPresenterTest {
    private val getBackpack = mockk<GetBackpack>()
    private val searchPokemon =  mockk<SearchPokemon>()
    private val catchPokemon = mockk<CatchPokemon>()
    private val view = mockk<BackpackView>(relaxed = true)
    private lateinit var presenter : BackpackPresenter

    @Before
    fun setUp() {
        presenter = BackpackPresenter(getBackpack, searchPokemon, catchPokemon)
        presenter.view = view

        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should call getBackpack with OrderBy CATCHED when calls renderBackpackOrderBy`() = runBlocking {
        coEvery {
            getBackpack(Ordenation.CATCHED.typeOrdenation)
        } returns Either.right(emptyList())

        presenter.orderBy = Ordenation.ORDER
        presenter.renderBackpackOrderBy()

        coVerifyOrder {
            presenter.view?.showLoading()
            presenter.view?.cleanList()
            getBackpack(Ordenation.CATCHED.typeOrdenation)
            presenter.view?.hideLoading()
        }
    }

    @Test
    fun `should call getBackpack with OrderBy ORDER when calls renderBackpackOrderBy`() = runBlocking {
        coEvery {
            getBackpack(Ordenation.ORDER.typeOrdenation)
        } returns Either.right(emptyList())

        presenter.orderBy = Ordenation.CATCHED
        presenter.renderBackpackOrderBy()

        coVerifyOrder {
            presenter.view?.showLoading()
            presenter.view?.cleanList()
            getBackpack(Ordenation.ORDER.typeOrdenation)
            presenter.view?.hideLoading()
        }
    }
}