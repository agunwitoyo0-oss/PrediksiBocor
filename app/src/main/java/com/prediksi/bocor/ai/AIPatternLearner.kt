package com.prediksi.bocor.ai

import com.prediksi.bocor.model.PredictionResult
import kotlin.math.abs

class AIPatternLearner {
    private val history = mutableListOf<String>()
    private val patternWeights = mutableMapOf<String, Double>()

    fun addHistory(number: String) {
        history.add(number)
        if (history.size > 100) history.removeAt(0)
        learnPatterns()
    }

    private fun learnPatterns() {
        patternWeights.clear()
        if (history.size < 3) return

        for (i in 0 until history.size - 1) {
            val current = history[i]
            val next = history[i + 1]
            val key = "$current->$next"
            patternWeights[key] = (patternWeights[key] ?: 0.0) + 1.0
        }

        val max = patternWeights.values.maxOrNull() ?: 1.0
        patternWeights.replaceAll { _, v -> v / max }
    }

    fun analyze(results: List<PredictionResult>): PredictionResult {
        if (results.isEmpty()) {
            return PredictionResult("AI Pattern", listOf("--"), 0, "Tidak ada data")
        }

        val allNumbers = results.flatMap { it.numbers }
        val frequency = allNumbers.groupingBy { it }.eachCount()

        val scored = frequency.map { (num, count) ->
            val baseScore = count.toDouble()
            val patternBonus = calculatePatternBonus(num)
            val trendScore = calculateTrend(num)
            num to (baseScore + patternBonus + trendScore)
        }.sortedByDescending { it.second }

        val topNumbers = scored.take(4).map { it.first }
        val avgConfidence = results.map { it.confidence }.average().toInt()
        val aiBoost = if (history.size > 10) 8 else 3
        val finalConfidence = (avgConfidence + aiBoost).coerceAtMost(98)

        return PredictionResult(
            method = "🤖 AI Smart Analysis",
            numbers = topNumbers,
            confidence = finalConfidence,
            description = "AI menganalisis ${history.size} pola history dengan kombinasi 4 metode prediksi"
        )
    }

    private fun calculatePatternBonus(number: String): Double {
        if (history.isEmpty()) return 0.0
        val lastNumber = history.lastOrNull() ?: return 0.0
        val key = "$lastNumber->$number"
        return (patternWeights[key] ?: 0.0) * 2.0
    }

    private fun calculateTrend(number: String): Double {
        if (history.size < 5) return 0.0
        val recentNumbers = history.takeLast(10).mapNotNull { it.toIntOrNull() }
        val targetNum = number.toIntOrNull() ?: return 0.0
        val avg = recentNumbers.average()
        val distance = abs(targetNum - avg)
        return (50.0 - distance).coerceAtLeast(0.0) / 50.0
    }

    fun getHistorySize(): Int = history.size
}
