package edu.quinnipiac.ser210.rezippy.api.AIData.Response

import edu.quinnipiac.ser210.rezippy.api.AIData.Content

data class Candidate(
    val avgLogprobs: Double,
    val content: Content,
    val finishReason: String
)