package com.iliaberlana.wefoxpokedex.domain.exception

sealed class DomainError : Exception() {
    object PokemonCatched : DomainError()
    object NoPokemonFound : DomainError()
    object NoInternetConnectionException : DomainError()
    object UnknownException: DomainError()
}