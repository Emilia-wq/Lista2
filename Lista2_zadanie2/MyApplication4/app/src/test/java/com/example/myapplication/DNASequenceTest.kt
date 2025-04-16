package com.example.myapplication
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DNASequenceTest {
    private lateinit var dnaSequence: DNASequence

    @Before
    fun setUp() {
        dnaSequence = DNASequence("TEST_DNA", "ATCGGCTA")
    }

    @Test
    fun testConstructor() {
        assertEquals("TEST_DNA", dnaSequence.identifier)
        assertEquals("ATCGGCTA", dnaSequence.data)
        assertEquals(8, dnaSequence.length)
    }

    @Test
    fun testValidProperty() {
        assertTrue(dnaSequence.VALID)

        // Test z nieprawidłową sekwencją
        val invalidDNA = DNASequence("INVALID", "ATCGXCTA")
        assertFalse(invalidDNA.VALID)
    }

    @Test
    fun testToString() {
        val expected = ">TEST_DNA\nATCGGCTA"
        assertEquals(expected, dnaSequence.toString())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMutateWithInvalidNucleotide() {
        dnaSequence.mutate(2, 'X') // Nieprawidłowa zasada
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMutateWithInvalidPosition() {
        dnaSequence.mutate(10, 'A') // Pozycja poza zakresem
    }

    @Test
    fun testFindMotif() {
        assertEquals(3, dnaSequence.findMotif("GGC"))
        assertEquals(-1, dnaSequence.findMotif("AAA"))
        assertEquals(0, dnaSequence.findMotif("ATC"))
    }

    @Test
    fun testComplement() {
        val complementary = dnaSequence.complement()
        assertEquals("TAGCCGAT", complementary)
    }

    @Test
    fun testTranscribe() {
        val rnaSequence = dnaSequence.transcribe()
        assertEquals("TEST_DNA", rnaSequence.identifier)
        assertEquals("AUCGGCUA", rnaSequence.data)
        assertEquals(8, rnaSequence.length)
        assertTrue(rnaSequence.VALID)
    }
}