package com.iliaberlana.wefoxpokedex.framework.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iliaberlana.wefoxpokedex.domain.entities.Pokemon
import java.util.*

@Entity
data class PokemonDbEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val order: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val weight: Int,
    @ColumnInfo val height: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo val experience: Int,
    @ColumnInfo(name = "types_pokemon") val typesPokemon: String
) {
    fun toDomain(): Pokemon =
        Pokemon(id, order, name, Date(), weight, height, imageUrl, experience, listOf(typesPokemon))

    companion object {
        fun fromDomain(pokemon: Pokemon): PokemonDbEntity = PokemonDbEntity(
            pokemon.id,
            pokemon.order,
            pokemon.name,
            pokemon.weight,
            pokemon.height,
            pokemon.imageUrl,
            pokemon.experience,
            pokemon.typesPokemon.joinToString(", ")
        )
    }
}