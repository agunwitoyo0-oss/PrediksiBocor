package com.prediksi.bocor.methods

import com.prediksi.bocor.model.PredictionResult

object MistikLama {
    private val mistikTable = mapOf(
        0 to 5, 1 to 6, 2 to 7, 3 to 8, 4 to 9,
        5 to 0, 6 to 1, 7 to 2, 8 to 3, 9 to 4
    )

    fun predict(input: String): PredictionResult {
        val digits = input.filter { it.isDigit() }.map { it.digitToInt() }
        if (digits.isEmpty()) {
            return PredictionResult("Mistik Lama", listOf("--", "--"), 0, "Input tidak valid")
        }

        val results = mutableListOf<String>()
        for (i in digits.indices) {
            for (j in i + 1 until digits.size) {
                val a = mistikTable[digits[i]] ?: 0
                val b = mistikTable[digits[j]] ?: 0
                results.add("$a$b")
            }
        }

        val topResults = results.distinct().take(4).ifEmpty { listOf("00", "55") }
        val confidence = (65..85).random()

        return PredictionResult(
            method = "Mistik Lama",
            numbers = topResults,
            confidence = confidence,
            description = "Konversi angka berdasarkan tabel mistik klasik (0↔5, 1↔6, dst)"
        )
    }
}
