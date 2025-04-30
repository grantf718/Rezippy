package edu.quinnipiac.ser210.rezippy.api.AIData.Response

data class UsageMetadata(
    val candidatesTokenCount: Int,
    val candidatesTokensDetails: List<CandidatesTokensDetail>,
    val promptTokenCount: Int,
    val promptTokensDetails: List<PromptTokensDetail>,
    val totalTokenCount: Int
)