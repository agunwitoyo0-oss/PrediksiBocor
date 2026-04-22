package com.prediksi.bocor.methods

import com.prediksi.bocor.model.PredictionResult

object MistikBaru {
    private val mistikBaruTable = mapOf(
        0 to 1, 1 to 2, 2 to 3, 3 to 4, 4 to 5,
        5 to 6, 6 to 7, 7 to 8, 8 to 9, 9 to 0
    )

    fun predict(input: String): PredictionResult {
        val digits = input.filter { it.isDigit() }.map { it.digitToInt() }
        if (digits.isEmpty()) {
            return PredictionResult("Mistik Baru", listOf("--", "--"), 0, "Input tidak valid")
        }

        val results = mutableListOf<String>()
        for (d in digits) {
            val next = mistikBaruTable[d] ?: 0
            val prev = mistikBaruTable[(d + 9) % 10] ?: 0
            results.add("$next$prev")
            results.add("$prev$next")
        }

        val topResults = results.distinct().take(4).ifEmpty { listOf("12", "89") }
        val confidence = (70..88).random()

        return PredictionResult(
            method = "Mistik Baru",
            numbers = topResults,
            confidence = confidence,
            description = "Pergeseran urutan modern berdasarkan siklus numerik (+1/-1)"
        )
    }
}
