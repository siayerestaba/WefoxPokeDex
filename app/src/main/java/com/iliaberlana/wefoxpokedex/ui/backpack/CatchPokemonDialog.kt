package com.iliaberlana.wefoxpokedex.ui.backpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.iliaberlana.wefoxpokedex.R
import com.iliaberlana.wefoxpokedex.ui.commons.loadImage
import com.iliaberlana.wefoxpokedex.ui.model.PokemonUI
import kotlinx.android.synthetic.main.pokemon_catch_dialog.view.*
import kotlinx.android.synthetic.main.pokemon_detail.*

class CatchPokemonDialog : DialogFragment() {

    private lateinit var pokemonUI: PokemonUI

    companion object {
        const val POKEMON_DATA = "pokemon_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(POKEMON_DATA)) {
                pokemonUI = it.getSerializable(POKEMON_DATA) as PokemonUI
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pokemon_catch_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.dialogPokeName.text = pokemonUI.name
        view.dialogPokeImage.loadImage(pokemonUI.imageUrl)
        view.dialogPokeWeight.text = resources.getString(R.string.pokemon_weight, " ${pokemonUI.weight}")
        view.dialogPokeHeight.text = resources.getString(R.string.pokemon_height, " ${pokemonUI.height}")

        view.acceptCatch.setOnClickListener {
            val dialogListener = activity as DialogListener
            dialogListener.onFinishDialogWithCatch(pokemonUI)
            dismiss()
        }
        view.cancelCatch.setOnClickListener { dismiss() }

        dialog.setCancelable(false)
        dialog.setTitle(pokemonUI.name)
    }

    interface DialogListener {
        fun onFinishDialogWithCatch(pokemonUI: PokemonUI)
    }
}