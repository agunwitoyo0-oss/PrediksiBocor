package com.prediksi.bocor.methods

import com.prediksi.bocor.model.PredictionResult

object TaysenShio {
    private val shioMap = mapOf(
        1 to "Tikus", 2 to "Kerbau", 3 to "Macan", 4 to "Kelinci",
        5 to "Naga", 6 to "Ular", 7 to "Kuda", 8 to "Kambing",
        9 to "Monyet", 10 to "Ayam", 11 to "Anjing", 12 to "Babi"
    )

    private val shioNumbers = mapOf(
        1 to listOf("01", "13", "25", "37", "49", "61", "73", "85", "97"),
        2 to listOf("02", "14", "26", "38", "50", "62", "74", "86", "98"),
        3 to listOf("03", "15", "27", "39", "51", "63", "75", "87", "99"),
        4 to listOf("04", "16", "28", "40", "52", "64", "76", "88", "00"),
        5 to listOf("05", "17", "29", "41", "53", "65", "77", "89"),
        6 to listOf("06", "18", "30", "42", "54", "66", "78", "90"),
        7 to listOf("07", "19", "31", "43", "55", "67", "79", "91"),
        8 to listOf("08", "20", "32", "44", "56", "68", "80", "92"),
        9 to listOf("09", "21", "33", "45", "57", "69", "81", "93"),
        10 to listOf("10", "22", "34", "46", "58", "70", "82", "94"),
        11 to listOf("11", "23", "35", "47", "59", "71", "83", "95"),
        12 to listOf("12", "24", "36", "48", "60", "72", "84", "96")
    )

    fun predict(input: String): PredictionResult {
        val digits = input.filter { it.isDigit() }.map { it.digitToInt() }
        if (digits.isEmpty()) {
            return PredictionResult("Taysen Shio", listOf("--", "--"), 0, "Input tidak valid")
        }

        val shioIndex = (digits.sum() % 12) + 1
        val shioName = shioMap[shioIndex] ?: "Tikus"
        val numbers = shioNumbers[shioIndex] ?: listOf("01", "13")
        val topNumbers = numbers.shuffled().take(4)

        val confidence = (68..86).random()

        return PredictionResult(
            method = "Taysen Shio",
            numbers = topNumbers,
            confidence = confidence,
            description = "Shio: $shioName - Angka keberuntungan tradisional Tionghoa"
        )
    }
}
