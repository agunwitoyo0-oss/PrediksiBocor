package com.prediksi.bocor.methods

import com.prediksi.bocor.model.PredictionResult

object Indeks {
    fun predict(input: String): PredictionResult {
        val digits = input.filter { it.isDigit() }.map { it.digitToInt() }
        if (digits.size < 2) {
            return PredictionResult("Indeks", listOf("--", "--"), 0, "Butuh minimal 2 digit")
        }

        val results = mutableListOf<String>()
        val sum = digits.sum() % 100
        val diff = kotlin.math.abs(digits.first() - digits.last())
        val mul = (digits.first() * digits.last() + 7) % 100

        results.add(sum.toString().padStart(2, '0'))
        results.add(diff.toString().padStart(2, '0') + (sum % 10))
        results.add(mul.toString().padStart(2, '0'))
        results.add(((sum + diff) % 100).toString().padStart(2, '0'))

        val confidence = (72..90).random()

        return PredictionResult(
            method = "Indeks",
            numbers = results.distinct().take(4),
            confidence = confidence,
            description = "Kalkulasi indeks numerik: jumlah, selisih, dan perkalian angka"
        )
    }
}
