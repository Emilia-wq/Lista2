package com.example.myapplication

/**
 * Klasa reprezentująca sekwencję DNA.
 *
 * @property identifier - Unikalny identyfikator sekwencji.
 * @property data - Ciąg znaków reprezentujący zasady DNA (A, C, G, T).
 */
class DNASequence(val identifier: String, var data: String) {

    /** Zbiór dopuszczalnych znaków w sekwencji DNA.*/
    val VALID_CHARS = setOf('A', 'C', 'G', 'T')

    /** Właściwość weryfikująca poprawność sekwencji DNA. */
    val VALID: Boolean
        get() = data.all { it in VALID_CHARS }

    /** Długość sekwencji DNA. */
    val length: Int
        get() = data.length

    /**
     * Zwraca reprezentację sekwencji w formacie FASTA.
     *
     * @return Tekst FASTA
     */
    override fun toString(): String {
        return ">$identifier\n$data"
    }

    /**
     * Mutuje zasadę na podanej pozycji.
     *
     * @param position - Indeks zasady do zmiany.
     * @param value - Nowa wartość zasady.
     */
    fun mutate(position: Int, value: Char) {
        require(value in VALID_CHARS) { "Niewłaściwa zasada DNA: $value" }
        require(position in 0 until length) { "Nieprawidłowa pozycja: $position" }

        val mutableData = data.toMutableList()
        mutableData[position] = value
        data = mutableData.joinToString("")
    }

    /**
     * Wyszukuje motyw w sekwencji DNA.
     *
     * @param motif - Ciąg zasad do wyszukania.
     * @return Indeks pierwszego wystąpienia lub -1.
     */
    fun findMotif(motif: String): Int {
        return data.indexOf(motif)
    }

    /**
     * Zwraca nić komplementarną do obecnej sekwencji DNA.
     *
     * @return Komplementarna sekwencja DNA.
     */
    fun complement(): String {
        return data.map { nucleotide ->
            when (nucleotide) {
                'A' -> 'T'
                'T' -> 'A'
                'C' -> 'G'
                'G' -> 'C'
                else -> nucleotide
            }
        }.joinToString("")
    }

    /**
     * Transkrybuje sekwencję DNA do RNA.
     *
     * @return Obiekt klasy [RNASequence].
     */
    fun transcribe(): RNASequence {
        val rnaData = data.map { if (it == 'T') 'U' else it }.joinToString("")
        return RNASequence(identifier, rnaData)
    }
}

/**
 * Klasa reprezentująca sekwencję RNA.
 *
 * @property identifier - Identyfikator sekwencji RNA.
 * @property data - Ciąg znaków RNA (A, C, G, U).
 */
class RNASequence(val identifier: String, var data: String) {

    /** Dopuszczalne zasady RNA. */
    val VALID_CHARS = setOf('A', 'C', 'G', 'U')

    /** Właściwość weryfikująca poprawność sekwencji RNA. */
    val VALID: Boolean
        get() = data.all { it in VALID_CHARS }

    /** Długość sekwencji RNA. */
    val length: Int
        get() = data.length

    /**
     * Zwraca reprezentację FASTA sekwencji RNA.
     *
     * @return Linia nagłówka + dane sekwencji.
     */
    override fun toString(): String {
        return ">$identifier\n$data"
    }

    /**
     * Mutuje zasadę na danej pozycji w sekwencji RNA.
     *
     * @param position - Indeks do mutacji.
     * @param value - Nowa zasada.
     */
    fun mutate(position: Int, value: Char) {
        require(value in VALID_CHARS) { "Niewłaściwa zasada RNA: $value" }
        require(position in 0 until length) { "Nieprawidłowa pozycja: $position" }

        val mutableData = data.toMutableList()
        mutableData[position] = value
        data = mutableData.joinToString("")
    }

    /**
     * Szuka motywu w RNA.
     *
     * @param motif - Poszukiwany motyw.
     * @return Indeks pierwszego wystąpienia.
     */
    fun findMotif(motif: String): Int {
        return data.indexOf(motif)
    }

    /**
     * Zwraca komplementarną nić RNA.
     *
     * @return Komplementarna sekwencja RNA.
     */
    fun complement(): String {
        return data.map { nucleotide ->
            when (nucleotide) {
                'A' -> 'U'
                'U' -> 'A'
                'C' -> 'G'
                'G' -> 'C'
                else -> nucleotide
            }
        }.joinToString("")
    }

    /**
     * Translacja RNA do białka według kodu genetycznego.
     *
     * @return Sekwencja białkowa jako [ProteinSequence].
     */
    fun transcribe(): ProteinSequence {
        // Słownik kodonów RNA do aminokwasów
        val codonTable = mapOf(
            // U
            "UUU" to 'F', "UUC" to 'F', "UUA" to 'L', "UUG" to 'L',
            "UCU" to 'S', "UCC" to 'S', "UCA" to 'S', "UCG" to 'S',
            "UAU" to 'Y', "UAC" to 'Y', "UAA" to '*', "UAG" to '*',
            "UGU" to 'C', "UGC" to 'C', "UGA" to '*', "UGG" to 'W',
            // C
            "CUU" to 'L', "CUC" to 'L', "CUA" to 'L', "CUG" to 'L',
            "CCU" to 'P', "CCC" to 'P', "CCA" to 'P', "CCG" to 'P',
            "CAU" to 'H', "CAC" to 'H', "CAA" to 'Q', "CAG" to 'Q',
            "CGU" to 'R', "CGC" to 'R', "CGA" to 'R', "CGG" to 'R',
            // A
            "AUU" to 'I', "AUC" to 'I', "AUA" to 'I', "AUG" to 'M',
            "ACU" to 'T', "ACC" to 'T', "ACA" to 'T', "ACG" to 'T',
            "AAU" to 'N', "AAC" to 'N', "AAA" to 'K', "AAG" to 'K',
            "AGU" to 'S', "AGC" to 'S', "AGA" to 'R', "AGG" to 'R',
            // G
            "GUU" to 'V', "GUC" to 'V', "GUA" to 'V', "GUG" to 'V',
            "GCU" to 'A', "GCC" to 'A', "GCA" to 'A', "GCG" to 'A',
            "GAU" to 'D', "GAC" to 'D', "GAA" to 'E', "GAG" to 'E',
            "GGU" to 'G', "GGC" to 'G', "GGA" to 'G', "GGG" to 'G'
        )

        // Tworzymy sekwencję białkową
        val proteinData = StringBuilder()

        // Przetwarzamy kodony (potrójki zasad)
        for (i in 0 until data.length step 3) {
            if (i + 2 < data.length) {
                val codon = data.substring(i, i + 3)
                val aminoAcid = codonTable[codon] ?: '?'

                // Jeśli kodony STOP (*, czyli UAA, UAG, UGA) przerywamy
                if (aminoAcid == '*') {
                    break
                }

                proteinData.append(aminoAcid)
            }
        }

        return ProteinSequence(identifier, proteinData.toString())
    }
}

/**
 * Klasa reprezentująca sekwencję białkową.
 *
 * @property identifier - Identyfikator sekwencji.
 * @property data - Sekwencja aminokwasów.
 */
class ProteinSequence(val identifier: String, var data: String) {

    /** Lista dozwolonych aminokwasów. */
    val VALID_CHARS = setOf(
        'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L',
        'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y'
    )

    /** Właściwość weryfikująca poprawność sekwencji białkowej. */
    val VALID: Boolean
        get() = data.all { it in VALID_CHARS }

    /** Długość sekwencji białkowej. */
    val length: Int
        get() = data.length

    /**
     * Mutuje aminokwas w sekwencji.
     *
     * @param position - Indeks aminokwasu.
     * @param value - Nowy aminokwas.
     */
    fun mutate(position: Int, value: Char) {
        require(value in VALID_CHARS) { "Niewłaściwy aminokwas: $value" }
        require(position in 0 until length) { "Nieprawidłowa pozycja: $position" }

        val mutableData = data.toMutableList()
        mutableData[position] = value
        data = mutableData.joinToString("")
    }

    /**
     * Zwraca reprezentację FASTA sekwencji białka.
     *
     * @return FASTA format: >id + dane.
     */
    override fun toString(): String {
        return ">$identifier\n$data"
    }

    /**
     * Szuka motywu w sekwencji białka.
     *
     * @param motif - Szukany ciąg.
     * @return Indeks pierwszego wystąpienia.
     */
    fun findMotif(motif: String): Int {
        return data.indexOf(motif)
    }
}

/**
 * Funkcja przedstawiająca działanie klas [DNASequence], [RNASequence] i [ProteinSequence].
 */
fun main() {
    // Tworzymy sekwencję DNA
    val dna = DNASequence("DNA_Seq1", "ATCGGCTA")
    println("DNA Sequence: \n$dna")
    println("DNA Length: ${dna.length}")
    println("DNA Valid: ${dna.VALID}")

    // Mutacja w DNA - zmiana zasady na zadanej pozycji
    dna.mutate(2, 'A')  // Zmieniamy trzecią zasadę na 'A'
    println("\nDNA Sequence after mutation: \n$dna")

    // Szukamy motywu w DNA
    val motifIndex = dna.findMotif("GCTA")
    println("\nMotif 'GCTA' found at position: $motifIndex")

    // Komplementarna nić do DNA
    val dnaComplement = dna.complement()
    println("\nComplementary DNA sequence: $dnaComplement")

    // Transkrypcja DNA do RNA
    val rna = dna.transcribe()
    println("\nTranscription to RNA: \n$rna")
    println("RNA Valid: ${rna.VALID}")

    // Tworzymy sekwencję RNA
    val rna2 = RNASequence("RNA_Seq1", "AUGCGAACGUGA")
    println("\nRNA Sequence: \n$rna2")

    // Mutacja w RNA
    rna2.mutate(4, 'G')
    println("\nRNA Sequence after mutation: \n$rna2")

    // Komplementarna nić RNA
    val rnaComplement = rna2.complement()
    println("\nComplementary RNA sequence: $rnaComplement")

    // Translacja RNA do białka
    val protein = rna2.transcribe()
    println("\nTranslation to Protein: \n$protein")
    println("Protein Valid: ${protein.VALID}")

    // Tworzymy sekwencję białka
    val protein2 = ProteinSequence("Protein_Seq1", "MFVAG")
    println("\nProtein Sequence: \n$protein2")

    // Mutacja w białku
    protein2.mutate(2, 'L')  // Zmieniamy trzeci aminokwas na 'L'
    println("\nProtein Sequence after mutation: \n$protein2")

    // Znalezienie motywu w białku
    val proteinMotifIndex = protein2.findMotif("AG")
    println("\nMotif 'AG' found at position: $proteinMotifIndex")
}