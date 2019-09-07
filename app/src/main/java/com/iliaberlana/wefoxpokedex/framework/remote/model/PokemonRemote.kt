package com.iliaberlana.wefoxpokedex.framework.remote.model

import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import java.util.*

data class PokemonRemote(
    val id: Int,
    val order: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val base_experience: Int,
    val sprites: Sprites,
    val types: List<Types>

) {
    fun toDomain(): Pokemon {
        val listTypes = types.fold(emptyList<String>()) { acc, item -> acc + listOf(item.type.name)}

        return Pokemon(id, order, name, Date(), weight, height, sprites.front_default, base_experience, listTypes)
    }
}

data class Sprites(
    val front_default: String
)

data class Types(
    val type: Type
)

data class Type(
    val name: String
)
