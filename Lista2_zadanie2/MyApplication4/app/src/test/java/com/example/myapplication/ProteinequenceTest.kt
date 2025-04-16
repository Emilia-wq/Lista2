package com.example.myapplication
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProteinequenceTest {
    private lateinit var proteinSequence: ProteinSequence

    @Before
    fun setUp() {
        proteinSequence = ProteinSequence("TEST_PROTEIN", "MFVAG")
    }

    @Test
    fun testConstructor() {
        assertEquals("TEST_PROTEIN", proteinSequence.identifier)
        assertEquals("MFVAG", proteinSequence.data)
        assertEquals(5, proteinSequence.length)
    }

    @Test
    fun testValidProperty() {
        assertTrue(proteinSequence.VALID)

        // Test z nieprawidłowym aminokwasem
        val invalidProtein = ProteinSequence("INVALID", "MFVXG")
        assertFalse(invalidProtein.VALID)
    }

    @Test
    fun testToString() {
        val expected = ">TEST_PROTEIN\nMFVAG"
        assertEquals(expected, proteinSequence.toString())
    }

    @Test
    fun testMutate() {
        proteinSequence.mutate(2, 'L')
        assertEquals("MFLAG", proteinSequence.data)

        // Test mutacji na innej pozycji
        proteinSequence.mutate(0, 'P')
        assertEquals("PFLAG", proteinSequence.data)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMutateWithInvalidAminoAcid() {
        proteinSequence.mutate(2, 'X') // X nie jest standardowym aminokwasem
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMutateWithInvalidPosition() {
        proteinSequence.mutate(10, 'A') // Pozycja poza zakresem
    }

    @Test
    fun testFindMotif() {
        assertEquals(2, proteinSequence.findMotif("VA"))
        assertEquals(-1, proteinSequence.findMotif("TRP"))
        assertEquals(0, proteinSequence.findMotif("MF"))
    }

    @Test
    fun testAllowedAminoAcids() {
        // Sprawdzenie, czy wszystkie standardowe aminokwasy są akceptowane
        val allAminoAcids = "ACDEFGHIKLMNPQRSTVWY"
        val allAAProtein = ProteinSequence("ALL_AA", allAminoAcids)

        assertTrue(allAAProtein.VALID)
        assertEquals(20, allAAProtein.length)
    }
}