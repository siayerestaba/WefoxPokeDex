package com.iliaberlana.wefoxpokedex.domain.entities

enum class Ordenation (val typeOrdenation: String) {
    ORDER("order"),
    CATCHED("catched");

    companion object {
        fun getOrdenationByType(type: String) =
            when(type) {
                ORDER.typeOrdenation -> ORDER
                CATCHED.typeOrdenation -> CATCHED
                else -> CATCHED
            }
    }
}