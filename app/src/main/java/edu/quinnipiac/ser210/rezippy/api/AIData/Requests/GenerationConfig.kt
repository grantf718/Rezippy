package edu.quinnipiac.ser210.rezippy.api.AIData.Requests

data class GenerationConfig(
    val candidateCount: Int,
    val maxOutputTokens: Int,
    val stopSequences: List<String>?,
    val temperature: Double?,
)