package com.iliaberlana.wefoxpokedex.domain.entities

import java.util.*

data class Pokemon(
    val id : Int,
    val order : Int,
    val name: String,
    val created: Date,
    val weight: Int,
    val height: Int,
    val imageUrl: String,
    val experience: Int,
    val typesPokemon: List<String>
)
