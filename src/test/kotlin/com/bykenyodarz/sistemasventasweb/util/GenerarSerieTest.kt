package com.bykenyodarz.sistemasventasweb.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenerarSerieTest {

    @Test
    fun testNumeroSerieRango1() {
        val gs = GenerarSerie()
        gs.numeroSerie(0)
        assertEquals(1, gs.dato)
        assertEquals("00000001", gs.numero)
    }

    @Test
    fun testNumeroSerieRango2() {
        val gs = GenerarSerie()
        gs.numeroSerie(9)
        assertEquals(10, gs.dato)
        assertEquals("00000010", gs.numero)
    }

    @Test
    fun testNumeroSerieRango3() {
        val gs = GenerarSerie()
        gs.numeroSerie(99)
        assertEquals(100, gs.dato)
        assertEquals("00000100", gs.numero)
    }

    @Test
    fun testNumeroSerieRango4() {
        val gs = GenerarSerie()
        gs.numeroSerie(999)
        assertEquals(1000, gs.dato)
        assertEquals("00001000", gs.numero)
    }

    @Test
    fun testNumeroSerieRango5() {
        val gs = GenerarSerie()
        gs.numeroSerie(9999)
        assertEquals(10000, gs.dato)
        assertEquals("00010000", gs.numero)
    }

    @Test
    fun testNumeroSerieRango6() {
        val gs = GenerarSerie()
        gs.numeroSerie(99999)
        assertEquals(100000, gs.dato)
        assertEquals("00100000", gs.numero)
    }

    @Test
    fun testNumeroSerieRango7() {
        val gs = GenerarSerie()
        gs.numeroSerie(999999)
        assertEquals(1000000, gs.dato)
        assertEquals("01000000", gs.numero)
    }

    @Test
    fun testNumeroSerieRango8() {
        val gs = GenerarSerie()
        gs.numeroSerie(9999999)
        assertEquals(10000000, gs.dato)
        assertEquals("10000000", gs.numero)
    }
}
