package com.iliaberlana.wefoxpokedex.framework.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iliaberlana.wefoxpokedex.framework.local.model.Converters
import com.iliaberlana.wefoxpokedex.framework.local.model.PokemonDbEntity

@Database(entities = [PokemonDbEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}

object PokemonDb {
    private lateinit var db: PokemonDatabase

    fun initializeDb(context: Context) {
        db = Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon-db"
        ).build()
    }

    fun getDb(): PokemonDatabase {
        if (!::db.isInitialized) {
            throw Error("Database not initialized")
        }
        return db
    }

}