package edu.quinnipiac.ser210.rezippy.api.AIData.Response

data class Candidate(
    val avgLogprobs: Double,
    val content: CandidateContent,
    val finishReason: String
)