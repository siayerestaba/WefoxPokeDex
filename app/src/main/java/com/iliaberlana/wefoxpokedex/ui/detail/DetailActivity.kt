package com.iliaberlana.wefoxpokedex.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.ui.commons.loadImage
import com.iliaberlana.wefoxpokedex.ui.commons.logDebug
import com.iliaberlana.wefoxpokedex.ui.commons.toast
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI

import kotlinx.android.synthetic.main.pokemon_detail.*
import org.koin.androidx.scope.currentScope

class DetailActivity : AppCompatActivity(), DetailView {
    private val presenter: DetailPresenter by currentScope.inject()
    private var actionbar: ActionBar? = null

    companion object {
        private const val POKEMON_KEY = "pokemon_key"

        fun open(activity: Activity, pokemonId: Int) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(POKEMON_KEY, pokemonId)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_detail)

        actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        val pokemonId = intent?.extras?.getInt(POKEMON_KEY)
        presenter.renderPokemon(pokemonId!!)
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }

    override fun updateActionBar(pokemonName: String) {
        actionbar!!.title = pokemonName

    }

    override fun showPokemon(pokemonUI: PokemonUI) {
        pokeImage.loadImage(pokemonUI.imageUrl)
        pokeName.text = pokemonUI.name

        pokeWeight.text = resources.getString(R.string.pokemon_weight, " ${pokemonUI.weight}")
        pokeHeight.text = resources.getString(R.string.pokemon_height, " ${pokemonUI.height}")
        pokeExperience.text = resources.getString(R.string.pokemon_experience, " ${pokemonUI.experience}")
        pokeDate.text = resources.getString(R.string.pokemon_date, "${pokemonUI.created}")
        pokeTypes.text = resources.getString(R.string.pokemon_types, pokemonUI.typesPokemon)
    }

    override fun hideLoading() {
        detailProgressbar.visibility = View.GONE
    }

    override fun showLoading() {
        detailProgressbar.visibility = View.VISIBLE
    }

    override fun showToastMessage(stringId: Int) {
        this.toast(this, resources.getString(stringId))
    }

    override fun showErrorCase(stringId: Int) {
    }

    override fun hideErrorCase() {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
