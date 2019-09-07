package com.iliaberlana.wefoxpokedex.usecases

import com.iliaberlana.wefoxpokedex.domain.entities.Ordenation
import org.junit.Assert.assertEquals
import org.junit.Test

class OrdenationTest {

    @Test
    fun `return ORDER when string is order`() {
        assertEquals(Ordenation.ORDER, Ordenation.getOrdenationByType("order"))
    }

    @Test
    fun `return CATCHED when string is catched`() {
        assertEquals(Ordenation.CATCHED, Ordenation.getOrdenationByType("catched"))
    }

    @Test
    fun `return CATCHED when string is empty`() {
        assertEquals(Ordenation.CATCHED, Ordenation.getOrdenationByType(""))
    }

    @Test
    fun `return CATCHED when string is random`() {
        assertEquals(Ordenation.CATCHED, Ordenation.getOrdenationByType("random"))
    }
}