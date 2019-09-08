package com.iliaberlana.wefoxpokedex.ui.backpack

import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.domain.entities.Ordenation
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI
import com.iliaberlana.wefoxpokedex.usecases.CatchPokemon
import com.iliaberlana.wefoxpokedex.usecases.GetBackpack
import com.iliaberlana.wefoxpokedex.usecases.SearchPokemon
import kotlinx.coroutines.*

class BackpackPresenter(
    private val getBackpack: GetBackpack,
    private val searchPokemon: SearchPokemon,
    private val catchPokemon: CatchPokemon
) : CoroutineScope by MainScope() {
    var view: BackpackView? = null
    var orderBy: Ordenation = Ordenation.CATCHED

    fun renderBackpack() = launch {
        view?.showLoading()
        view?.cleanList()

        val resultPokemons = withContext(Dispatchers.IO) { getBackpack(orderBy.typeOrdenation) }
        resultPokemons.fold({
            when (it) {
                DomainError.NoInternetConnectionException -> showToastErrorMessage(R.string.noInternetConectionError)
                DomainError.UnknownException -> showToastErrorMessage(R.string.unknownException)
            }
        }, { listPokemons ->
            when {
                listPokemons.isNullOrEmpty() -> {
                    showErrorMessage(R.string.welcome)
                }
                else -> {
                    view?.hideErrorCase()
                    view?.listPokemon(listPokemons.map { PokemonUI.fromDomain(it) })
                }
            }
        })

        view?.hideLoading()
    }

    fun searchAPokemonToCatch() = launch {
        view?.showLoading()

        val resultPokemon = withContext(Dispatchers.IO) { searchPokemon() }
        resultPokemon.fold({
            when (it) {
                DomainError.NoPokemonFound -> showToastErrorMessage(R.string.noPokemonFound)
                DomainError.NoInternetConnectionException -> showToastErrorMessage(R.string.noInternetConectionError)
                DomainError.UnknownException -> showToastErrorMessage(R.string.unknownException)
            }
        }, { pokemon ->
            view?.showPokemonToCatch(PokemonUI.fromDomain(pokemon))
        })


        view?.hideLoading()
    }

    fun catchNewPokemon(pokemonUI: PokemonUI) = launch {
        view?.showLoading()

        withContext(Dispatchers.IO) { catchPokemon(pokemonUI.toDomain()) }
        renderBackpack()
    }

    fun onPokemonClicked(pokemonUI: PokemonUI) {
        view?.showPokemon(pokemonUI)
    }

    fun renderBackpackOrderBy() {
        when (orderBy) {
            Ordenation.ORDER -> {
                orderBy = Ordenation.CATCHED
            }

            Ordenation.CATCHED -> {
                orderBy = Ordenation.ORDER
            }
        }

        renderBackpack()
    }

    private fun showErrorMessage(stringIdError: Int) {
        view?.showErrorCase(stringIdError)
    }

    private fun showToastErrorMessage(stringIdError: Int) {
        view?.showToastMessage(stringIdError)
    }

    fun onDestroy() {
        view = null
    }
}