package com.iliaberlana.wefoxpokedex.ui.commons

interface BaseView {
    fun hideLoading()
    fun showLoading()

    fun showToastMessage(stringId: Int)

    fun showErrorCase(stringId: Int)
    fun hideErrorCase()
}