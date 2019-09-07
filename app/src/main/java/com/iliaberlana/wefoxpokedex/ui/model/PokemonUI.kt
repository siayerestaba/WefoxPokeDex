package com.iliaberlana.wefoxpokedex.ui.model

import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import java.io.Serializable
import java.util.*

data class PokemonUI(
    val id: Int,
    val order: Int,
    val name: String,
    val created: Date,
    val weight: Int,
    val height: Int,
    val imageUrl: String,
    val experience: Int,
    val typesPokemon: String
) : Serializable {

    fun toDomain(): Pokemon =
        Pokemon(id, order, name, created, weight, height, imageUrl, experience, listOf(typesPokemon))

    override fun toString(): String {
        return "PokemonUI(id=$id, order=$order, name='$name', created=$created, weight=$weight, height=$height, imageUrl='$imageUrl', experience=$experience, typesPokemon='$typesPokemon')"
    }

    companion object {
        fun fromDomain(pokemon: Pokemon): PokemonUI = PokemonUI(
            pokemon.id,
            pokemon.order,
            pokemon.name,
            pokemon.created,
            pokemon.weight,
            pokemon.height,
            pokemon.imageUrl,
            pokemon.experience,
            pokemon.typesPokemon.joinToString(", ")
        )
    }


}