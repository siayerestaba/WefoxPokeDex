package com.iliaberlana.wefoxpokedex

import android.app.Application
import androidx.room.Room
import com.iliaberlana.wefoxpokedex.domain.data.PokemonRepository
import com.iliaberlana.wefoxpokedex.framework.PokemonRepositoryImpl
import com.iliaberlana.wefoxpokedex.framework.local.PokemonDatabase
import com.iliaberlana.wefoxpokedex.framework.local.PokemonDb
import com.iliaberlana.wefoxpokedex.framework.remote.NetworkFactory
import com.iliaberlana.wefoxpokedex.framework.remote.PokemonRemoteDataSource
import com.iliaberlana.wefoxpokedex.ui.backpack.BackpackActivity
import com.iliaberlana.wefoxpokedex.ui.backpack.BackpackPresenter
import com.iliaberlana.wefoxpokedex.ui.detail.DetailActivity
import com.iliaberlana.wefoxpokedex.ui.detail.DetailPresenter
import com.iliaberlana.wefoxpokedex.usecases.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

open class WefoxPokedexApp : Application() {

    override fun onCreate() {
        super.onCreate()

        PokemonDb.initializeDb(this)

        startKoin {
            androidLogger()
            androidContext(this@WefoxPokedexApp)
            modules(listOf(dbModule, appModule))

        }
    }

    private var apiUrlApp: String = "https://pokeapi.co"
    open fun getApiUrl(): String = apiUrlApp
    open fun setApiUrl(apiUrl: String) {
        this.apiUrlApp = apiUrl
    }

    private val dbModule = module {
        single {
            Room.databaseBuilder(androidApplication(), PokemonDatabase::class.java, "pokemon-db").build()
        }
    }

    private val appModule = module {
        single { NetworkFactory() }
        single { PokemonRemoteDataSource(get(), getApiUrl()) }

        single<PokemonRepository> { PokemonRepositoryImpl(get<PokemonDatabase>().pokemonDao(), get()) }

        single { CatchPokemon(get()) }
        single { GetPokemon(get()) }
        single { GetBackpack(get()) }
        single { SearchPokemon(get()) }

        scope(named<BackpackActivity>()) {
            scoped { BackpackPresenter(get(), get(), get()) }
        }

        scope(named<DetailActivity>()) {
            scoped { DetailPresenter(get()) }
        }
    }
}