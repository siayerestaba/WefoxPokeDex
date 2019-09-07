package com.iliaberlana.wefoxpokedex.ui.backpack

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.ui.commons.toast
import com.iliaberlana.wefoxpokedex.ui.detail.DetailActivity
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI
import kotlinx.android.synthetic.main.list_withprogressbar_text.*
import kotlinx.android.synthetic.main.pokemon_catch_dialog.*
import kotlinx.android.synthetic.main.pokemon_item.*
import kotlinx.android.synthetic.main.pokemon_item.pokemonName
import kotlinx.android.synthetic.main.pokemon_list.*
import org.koin.androidx.scope.currentScope

class BackpackActivity : AppCompatActivity(), BackpackView, CatchPokemonDialog.DialogListener {
    private val presenter: BackpackPresenter by currentScope.inject()
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_withprogressbar_text)

        presenter.renderBackpack()

        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        adapter = PokemonAdapter(presenter)

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        pokemonRecyclerview.adapter = adapter
        pokemonRecyclerview.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.pokemon_catch -> {
                presenter.searchAPokemonToCatch()
            }
            R.id.action_order -> {
                presenter.renderBackpackOrderBy()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onFinishDialogWithCatch(pokemonUI: PokemonUI) {
        presenter.catchNewPokemon(pokemonUI)
    }

    override fun listPokemon(pokemons: List<PokemonUI>) {
        adapter.addAll(pokemons)
    }

    override fun showPokemonToCatch(pokemonUI: PokemonUI) {
        val dialog = CatchPokemonDialog().apply {
            arguments = Bundle().apply {
                putSerializable(CatchPokemonDialog.POKEMON_DATA, pokemonUI)
            }
        }

        dialog.show(supportFragmentManager, "CatchPokemonDialog")
    }

    override fun showPokemon(pokemonUI: PokemonUI) {
        DetailActivity.open(activity = this, pokemonId = pokemonUI.id)
    }

    override fun cleanList() {
        adapter.clean()
    }

    override fun hideLoading() {
        listProgressbar.visibility = View.GONE
    }

    override fun showLoading() {
        listProgressbar.visibility = View.VISIBLE
    }

    override fun showToastMessage(stringId: Int) {
        this.toast(this, resources.getString(stringId))
    }

    override fun showErrorCase(stringId: Int) {
        listTexterror.text = resources.getString(stringId)
        listTexterror.visibility = View.VISIBLE
    }

    override fun hideErrorCase() {
        listTexterror.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
