package com.iliaberlana.wefoxpokedex.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM PokemonDbEntity ORDER BY created desc")
    suspend fun getAll(): List<PokemonDbEntity>

    @Insert
    suspend fun insert(pokemonDbEntity: PokemonDbEntity)

    @Query("SELECT * FROM PokemonDbEntity WHERE id = :id ")
    suspend fun getPokemon(id: Int): PokemonDbEntity?
}