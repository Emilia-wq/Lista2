package com.example.myapplication

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class RNASequenceTest {
private lateinit var rnaSequence: RNASequence

@Before
fun setUp() {
    rnaSequence = RNASequence("TEST_RNA", "AUCGAUCG")
}

@Test
fun testConstructor() {
    assertEquals("TEST_RNA", rnaSequence.identifier)
    assertEquals("AUCGAUCG", rnaSequence.data)
    assertEquals(8, rnaSequence.length)
}

@Test
fun testValidProperty() {
    assertTrue(rnaSequence.VALID)

    // Test z nieprawidłową sekwencją
    val invalidRNA = RNASequence("INVALID", "AUCGXTCG")
    assertFalse(invalidRNA.VALID)
}

@Test
fun testToString() {
    val expected = ">TEST_RNA\nAUCGAUCG"
    assertEquals(expected, rnaSequence.toString())
}

@Test
fun testMutate() {
    rnaSequence.mutate(4, 'G')
    assertEquals("AUCGGUCG", rnaSequence.data)

    // Test mutacji na innej pozycji
    rnaSequence.mutate(0, 'G')
    assertEquals("GUCGGUCG", rnaSequence.data)
}

@Test(expected = IllegalArgumentException::class)
fun testMutateWithInvalidNucleotide() {
    rnaSequence.mutate(2, 'T') // RNA używa U zamiast T
}

@Test(expected = IllegalArgumentException::class)
fun testMutateWithInvalidPosition() {
    rnaSequence.mutate(10, 'A') // Pozycja poza zakresem
}


@Test
fun testComplement() {
    val complementary = rnaSequence.complement()
    assertEquals("UAGCUAGC", complementary)
}

@Test
fun testTranscribe() {
    // Test translacji sekwencji kodującej konkretne aminokwasy
    val startCodon = RNASequence("START", "AUGGCACGU")
    val protein = startCodon.transcribe()

    assertEquals("START", protein.identifier)
    assertEquals("MAR", protein.data) // AUG -> M, GCA -> A, CGU -> R
    assertTrue(protein.VALID)
}

@Test
fun testTranscribeWithStopCodon() {
    // Test z kodonem STOP
    val withStop = RNASequence("STOP_TEST", "AUGGCAUAA") // UAA to kodon STOP
    val protein = withStop.transcribe()

    assertEquals("STOP_TEST", protein.identifier)
    assertEquals("MA", protein.data) // Translacja powinna się zatrzymać na kodonie STOP
}

@Test
fun testTranscribeWithIncompleteCodon() {
    // Test z niekompletnym kodonem
    val incomplete = RNASequence("INCOMPLETE", "AUGCA") // Ostatni kodon jest niekompletny
    val protein = incomplete.transcribe()

    assertEquals("INCOMPLETE", protein.identifier)
    assertEquals("M", protein.data) // Tylko pierwszy pełny kodon zostanie przetłumaczony
}
}