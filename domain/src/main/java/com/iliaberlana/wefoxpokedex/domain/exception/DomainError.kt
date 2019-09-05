package com.iliaberlana.wefoxpokedex.domain.exception

sealed class DomainError : Exception() {
    object NoInternetConnectionException : DomainError()
    object UnknownException: DomainError()
}