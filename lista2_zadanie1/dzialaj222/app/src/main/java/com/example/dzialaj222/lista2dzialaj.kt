package com.example.dzialaj222

import kotlin.math.abs
import kotlin.math.pow

/**
 * Klasa reprezentująca wielomian dowolnego stopnia
 *
 * @param lista - Lista współczynników od wyrazu wolnego do najwyższej potęgi.
 */
class Wielomian(lista: List<Double>) {

    /**
     * Lista współczynników wielomianu, od x^0 do x^n.
     */
    val współczynniki: MutableList<Double>

    /**
     * Konstruktor pomocniczy umożliwiający tworzenie wielomianu ze współczynników podanych jako vararg
     */
    constructor(vararg wartosci: Double) : this(wartosci.toList())

    init {
        // Kopiujemy listę, aby uniknąć modyfikacji oryginalnej listy
        val kopiaListy = lista.toMutableList()

        // Usuwamy zera z końca listy, aby wielomian miał poprawny stopień
        while (kopiaListy.size > 1 && kopiaListy.last() == 0.0) {
            kopiaListy.removeAt(kopiaListy.size - 1)
        }
        współczynniki = kopiaListy
    }

    /**
     * Zwraca stopień wielomianu.
     *
     * @return Najwyższy stopień.
     */
    fun stopien(): Int {
        return współczynniki.size - 1
    }

    /**
     * Zwraca tekstową reprezentację wielomianu w postaci "W(x) = ..."
     *
     * @return Łańcuch znaków reprezentujący wielomian.
     */
    override fun toString(): String {
        // Obsługa wielomianu zerowego
        if (współczynniki.all { it == 0.0 }) return "W(x) = 0"

        var wynik = "W(x) = "

        for (i in współczynniki.lastIndex downTo 0) {
            val aktualnyWspolczynnik = współczynniki[i]
            if (aktualnyWspolczynnik == 0.0) continue

            // Dodajemy znak plus lub minus
            if (wynik != "W(x) = ") {
                wynik += if (aktualnyWspolczynnik > 0) " + " else " - "
            } else if (aktualnyWspolczynnik < 0) {
                wynik += "-"
            }

            val wartoscBezwzgledna = abs(aktualnyWspolczynnik)

            // Formatujemy człon wielomianu w zależności od wykładnika
            wynik += when (i) {
                0 -> "$wartoscBezwzgledna"
                1 -> if (wartoscBezwzgledna == 1.0) "x" else "${formatujLiczbe(wartoscBezwzgledna)}x"
                else -> if (wartoscBezwzgledna == 1.0) "x^$i" else "${formatujLiczbe(wartoscBezwzgledna)}x^$i"
            }
        }

        return wynik
    }

    /**
     * Pomocnicza funkcja do formatowania liczb (usuwa trailing zeros)
     */
    private fun formatujLiczbe(liczba: Double): String {
        return if (liczba == liczba.toInt().toDouble()) {
            liczba.toInt().toString()
        } else {
            liczba.toString()
        }
    }

    /**
     * Oblicza wartość wielomianu dla podanej wartości x.
     *
     * @param x - Wartość argumentu.
     * @return Wynik działania wielomianu dla zadanej wartości x.
     */
    operator fun invoke(x: Double): Double {
        var wynik = 0.0

        for (i in współczynniki.indices) {
            wynik += współczynniki[i] * x.pow(i.toDouble())
        }

        return wynik
    }

    /**
     * Operator dodawania dwóch wielomianów.
     *
     * @param wielomian2 - Drugi wielomian, który ma zostać dodany.
     * @return Nowy obiekt klasy Wielomian będący sumą.
     */
    operator fun plus(wielomian2: Wielomian): Wielomian {
        val maxSize = maxOf(this.współczynniki.size, wielomian2.współczynniki.size)
        val wynik = MutableList(maxSize) { 0.0 }

        for (i in 0 until maxSize) {
            val a = if (i < this.współczynniki.size) this.współczynniki[i] else 0.0
            val b = if (i < wielomian2.współczynniki.size) wielomian2.współczynniki[i] else 0.0
            wynik[i] = a + b
        }

        return Wielomian(wynik)
    }

    /**
     * Operator odejmowania dwóch wielomianów.
     *
     * @param other - Wielomian, który ma zostać odjęty.
     * @return Nowy obiekt klasy Wielomian będący różnicą.
     */
    operator fun minus(other: Wielomian): Wielomian {
        val maxSize = maxOf(this.współczynniki.size, other.współczynniki.size)
        val wynik = MutableList(maxSize) { 0.0 }

        for (i in 0 until maxSize) {
            val a = if (i < this.współczynniki.size) this.współczynniki[i] else 0.0
            val b = if (i < other.współczynniki.size) other.współczynniki[i] else 0.0
            wynik[i] = a - b
        }

        return Wielomian(wynik)
    }

    /**
     * Operator mnożenia dwóch wielomianów.
     *
     * @param other - Drugi wielomian do przemnożenia.
     * @return Nowy obiekt klasy Wielomian będący iloczynem.
     */
    operator fun times(other: Wielomian): Wielomian {
        val wynikSize = this.współczynniki.size + other.współczynniki.size - 1
        val wynik = MutableList(wynikSize) { 0.0 }

        for (i in this.współczynniki.indices) {
            for (j in other.współczynniki.indices) {
                wynik[i + j] += this.współczynniki[i] * other.współczynniki[j]
            }
        }

        return Wielomian(wynik)
    }

    /**
     * Operator złożony dodawania (+=).
     *
     * @param other - Wielomian do dodania.
     */
    operator fun plusAssign(other: Wielomian) {
        val nowy = this + other
        aktualizujWspolczynniki(nowy.współczynniki)
    }

    /**
     * Operator złożony odejmowania (-=).
     *
     * @param other - Wielomian do odjęcia.
     */
    operator fun minusAssign(other: Wielomian) {
        val nowy = this - other
        aktualizujWspolczynniki(nowy.współczynniki)
    }

    /**
     * Operator złożony mnożenia (*=).
     *
     * @param other Wielomian do przemnożenia.
     */
    operator fun timesAssign(other: Wielomian) {
        val nowy = this * other
        aktualizujWspolczynniki(nowy.współczynniki)
    }

    /**
     * Pomocnicza metoda do aktualizacji współczynników
     */
    private fun aktualizujWspolczynniki(noweWspolczynniki: List<Double>) {
        this.współczynniki.clear()
        this.współczynniki.addAll(noweWspolczynniki)
    }

    /**
     * Operator porównania
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Wielomian) return false

        return współczynniki == other.współczynniki
    }

    /**
     * Generuje hashCode
     */
    override fun hashCode(): Int {
        return współczynniki.hashCode()
    }
}

/**
 * Funkcja główna prezentująca użycie klasy [Wielomian].
 *
 * Wykonywane są:
 * - tworzenie wielomianów,
 * - wypisywanie ich na ekran,
 * - obliczenie wartości W(x),
 * - operacje dodawania, odejmowania, mnożenia,
 * - użycie operatorów złożonych.
 */
fun main() {
    val w1 = Wielomian(1.0, 2.0)     // 2x + 1
    val w2 = Wielomian(3.0, 0.0, 4.0) // 4x^2 + 3

    println("W1: $w1")
    println("W2: $w2")

    println("Stopień W1: ${w1.stopien()}")
    println("W1(2.0) = ${w1(2.0)}")

    val suma = w1 + w2
    println("W1 + W2 = $suma")

    val roznica = w2 - w1
    println("W2 - W1 = $roznica")

    val iloczyn = w1 * w2
    println("W1 * W2 = $iloczyn")

    val w3 = Wielomian(1.0, 1.0)
    println("W3 przed: $w3")
    w3 += w1
    println("W3 po += W1: $w3")

    val w4 = Wielomian(5.0, 2.0, 1.0)  // x^2 + 2x + 5
    println("W4 przed: $w4")
    w4 -= w1
    println("W4 po -= W1: $w4")

    val w5 = Wielomian(1.0, 0.0, 1.0)  // x^2 + 1
    println("W5 przed: $w5")
    w5 *= w1
    println("W5 po *= W1: $w5")

    // Test wielomianu zerowego
    val w6 = Wielomian(0.0)
    println("Wielomian zerowy: $w6")

    // Test z zerami na końcu
    val w7 = Wielomian(1.0, 2.0, 0.0, 0.0)
    println("Wielomian z zerami na końcu: $w7")
}