package com.example.dzialaj222

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import kotlin.math.abs




class WielomianTest {

    private val DELTA = 1e-10

    @Test
    fun `test konstruktora z listą`() {
        val w = Wielomian(listOf(1.0, 2.0, 3.0))
        assertEquals(3, w.współczynniki.size)
        assertEquals(1.0, w.współczynniki[0])
        assertEquals(2.0, w.współczynniki[1])
        assertEquals(3.0, w.współczynniki[2])
    }

    @Test
    fun `test konstruktora z varargs`() {
        val w = Wielomian(1.0, 2.0, 3.0)
        assertEquals(3, w.współczynniki.size)
        assertEquals(1.0, w.współczynniki[0])
        assertEquals(2.0, w.współczynniki[1])
        assertEquals(3.0, w.współczynniki[2])
    }

    @Test
    fun `test usuwania zer z końca wielomianu`() {
        val w = Wielomian(1.0, 2.0, 0.0, 0.0)
        assertEquals(2, w.współczynniki.size)
        assertEquals(1.0, w.współczynniki[0])
        assertEquals(2.0, w.współczynniki[1])
    }

    @Test
    fun `test wielomianu zerowego`() {
        val w = Wielomian(0.0)
        assertEquals(1, w.współczynniki.size)
        assertEquals(0.0, w.współczynniki[0])
    }

    @Test
    fun `test metody stopień`() {
        val w1 = Wielomian(1.0, 2.0, 3.0)
        assertEquals(2, w1.stopien())

        val w2 = Wielomian(5.0)
        assertEquals(0, w2.stopien())

        val w3 = Wielomian(0.0, 0.0, 7.0, 0.0)
        assertEquals(2, w3.stopien())
    }

    @Test
    fun `test metody toString dla wielomianu zerowego`() {
        val w = Wielomian(0.0)
        assertEquals("W(x) = 0", w.toString())
    }


    @Test
    fun `test operatora plus`() {
        val w1 = Wielomian(1.0, 2.0)         // 1 + 2x
        val w2 = Wielomian(3.0, 0.0, 4.0)    // 3 + 0x + 4x^2
        val suma = w1 + w2                  // 4 + 2x + 4x^2

        assertEquals(3, suma.współczynniki.size)
        assertEquals(4.0, suma.współczynniki[0], DELTA)
        assertEquals(2.0, suma.współczynniki[1], DELTA)
        assertEquals(4.0, suma.współczynniki[2], DELTA)
    }

    @Test
    fun `test operatora minus`() {
        val w1 = Wielomian(1.0, 2.0)         // 1 + 2x
        val w2 = Wielomian(3.0, 0.0, 4.0)    // 3 + 0x + 4x^2

        val roznica1 = w2 - w1              // 2 - 2x + 4x^2
        assertEquals(3, roznica1.współczynniki.size)
        assertEquals(2.0, roznica1.współczynniki[0], DELTA)
        assertEquals(-2.0, roznica1.współczynniki[1], DELTA)
        assertEquals(4.0, roznica1.współczynniki[2], DELTA)

        val roznica2 = w1 - w2              // -2 + 2x - 4x^2
        assertEquals(3, roznica2.współczynniki.size)
        assertEquals(-2.0, roznica2.współczynniki[0], DELTA)
        assertEquals(2.0, roznica2.współczynniki[1], DELTA)
        assertEquals(-4.0, roznica2.współczynniki[2], DELTA)
    }

    @Test
    fun `test operatora times`() {
        val w1 = Wielomian(1.0, 2.0)         // 1 + 2x
        val w2 = Wielomian(3.0, 0.0, 4.0)    // 3 + 0x + 4x^2

        val iloczyn = w1 * w2               // 3 + 6x + 4x^2 + 8x^3
        assertEquals(4, iloczyn.współczynniki.size)
        assertEquals(3.0, iloczyn.współczynniki[0], DELTA)
        assertEquals(6.0, iloczyn.współczynniki[1], DELTA)
        assertEquals(4.0, iloczyn.współczynniki[2], DELTA)
        assertEquals(8.0, iloczyn.współczynniki[3], DELTA)
    }

    @Test
    fun `test operatora plusAssign`() {
        val w1 = Wielomian(1.0, 2.0)         // 1 + 2x
        val w2 = Wielomian(3.0, 4.0)         // 3 + 4x

        w1 += w2                           // 4 + 6x
        assertEquals(2, w1.współczynniki.size)
        assertEquals(4.0, w1.współczynniki[0], DELTA)
        assertEquals(6.0, w1.współczynniki[1], DELTA)
    }

    @Test
    fun `test operatora minusAssign`() {
        val w1 = Wielomian(5.0, 3.0)         // 5 + 3x
        val w2 = Wielomian(2.0, 1.0)         // 2 + x

        w1 -= w2                           // 3 + 2x
        assertEquals(2, w1.współczynniki.size)
        assertEquals(3.0, w1.współczynniki[0], DELTA)
        assertEquals(2.0, w1.współczynniki[1], DELTA)
    }

    @Test
    fun `test operatora timesAssign`() {
        val w1 = Wielomian(1.0, 1.0)         // 1 + x
        val w2 = Wielomian(2.0, 3.0)         // 2 + 3x

        w1 *= w2                           // 2 + 5x + 3x^2
        assertEquals(3, w1.współczynniki.size)
        assertEquals(2.0, w1.współczynniki[0], DELTA)
        assertEquals(5.0, w1.współczynniki[1], DELTA)
        assertEquals(3.0, w1.współczynniki[2], DELTA)
    }

    @Test
    fun `test operatora equals`() {
        val w1 = Wielomian(1.0, 2.0, 3.0)
        val w2 = Wielomian(1.0, 2.0, 3.0)
        val w3 = Wielomian(1.0, 2.0, 3.0, 0.0)
        val w4 = Wielomian(1.0, 2.0, 0.0)
        val w5 = Wielomian(0.0, 2.0, 3.0)

        assertTrue(w1 == w2)
        assertTrue(w1 == w3)
        assertFalse(w1 == w4)
        assertFalse(w1 == w5)
    }

    @Test
    fun `test normalizacji przy operacjach arytmetycznych`() {
        val w1 = Wielomian(1.0, 0.0, 1.0)    // 1 + 0x + x^2
        val w2 = Wielomian(0.0, 0.0, -1.0)   // -x^2

        val suma = w1 + w2                  // 1
        assertEquals(1, suma.współczynniki.size)
        assertEquals(1.0, suma.współczynniki[0], DELTA)
    }

    @Test
    fun `test wartości wielomianu dla różnych wartości x`() {
        val w = Wielomian(2.0, -3.0, 1.0)    // 2 - 3x + x^2

        // Dla x = 0: 2 - 3*0 + 0^2 = 2
        assertEquals(2.0, w(0.0), DELTA)

        // Dla x = 1: 2 - 3*1 + 1^2 = 2 - 3 + 1 = 0
        assertEquals(0.0, w(1.0), DELTA)

        // Dla x = 2: 2 - 3*2 + 2^2 = 2 - 6 + 4 = 0
        assertEquals(0.0, w(2.0), DELTA)

        // Dla x = 3: 2 - 3*3 + 3^2 = 2 - 9 + 9 = 2
        assertEquals(2.0, w(3.0), DELTA)

        // Dla x = -1: 2 - 3*(-1) + (-1)^2 = 2 + 3 + 1 = 6
        assertEquals(6.0, w(-1.0), DELTA)
    }

    @Test
    fun `test złożonych operacji na wielomianach`() {
        val w1 = Wielomian(1.0, 1.0)         // 1 + x
        val w2 = Wielomian(1.0, 0.0, 1.0)    // 1 + x^2
        val w3 = Wielomian(2.0, 2.0)         // 2 + 2x

        // (1 + x) + (1 + x^2) - (2 + 2x) = 0 - x + x^2
        val wynik = w1 + w2 - w3
        assertEquals(3, wynik.współczynniki.size)
        assertEquals(0.0, wynik.współczynniki[0], DELTA)
        assertEquals(-1.0, wynik.współczynniki[1], DELTA)
        assertEquals(1.0, wynik.współczynniki[2], DELTA)

        // Po normalizacji powinien zostać usunięty współczynnik przy x^0
        assertEquals("W(x) = x^2 - x", wynik.toString())
    }
}

