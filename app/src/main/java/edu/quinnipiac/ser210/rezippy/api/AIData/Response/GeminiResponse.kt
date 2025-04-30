package edu.quinnipiac.ser210.rezippy.api.AIData.Response

data class GeminiResponse(
    val candidates: List<Candidate>,
    val modelVersion: String,
    val usageMetadata: UsageMetadata
)