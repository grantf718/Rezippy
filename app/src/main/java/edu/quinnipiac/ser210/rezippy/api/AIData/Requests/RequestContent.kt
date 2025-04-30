package edu.quinnipiac.ser210.rezippy.api.AIData.Requests

import edu.quinnipiac.ser210.rezippy.api.AIData.TextPart

data class RequestContent(
    val parts: List<TextPart>,
    val role: String
)
