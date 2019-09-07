package com.iliaberlana.wefoxpokedex.ui.detail

import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.domain.exception.DomainError
import com.iliaberlana.wefoxpokedex.ui.commons.logDebug
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI
import com.iliaberlana.wefoxpokedex.usecases.GetPokemon
import kotlinx.coroutines.*

class DetailPresenter(
    private val getPokemon: GetPokemon
) : CoroutineScope by MainScope() {
    var view: DetailView? = null

    fun renderPokemon(pokemonId: Int) = launch {
        view?.showLoading()

        val resultPokemon = withContext(Dispatchers.IO) { getPokemon(pokemonId) }
        resultPokemon.fold({
            when (it) {
                DomainError.NoPokemonFound -> showErrorMessage(R.string.noPokemonFound)
                DomainError.UnknownException -> showErrorMessage(R.string.unknownException)
            }
        }, { pokemon ->
            view?.showPokemon(PokemonUI.fromDomain(pokemon))
        })

        view?.hideLoading()
    }

    private fun showErrorMessage(stringIdError: Int) {
        view?.showToastMessage(stringIdError)
    }

    fun onDestroy() {
        view = null
    }
}