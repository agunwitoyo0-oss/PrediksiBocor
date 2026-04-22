package com.prediksi.bocor.model

data class PredictionResult(
    val method: String,
    val numbers: List<String>,
    val confidence: Int,
    val description: String
)
