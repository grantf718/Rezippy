package edu.quinnipiac.ser210.rezippy.api.AIData

import edu.quinnipiac.ser210.rezippy.api.AIData.Response.FunctionCall

data class Part(
    val functionCall: FunctionCall? = null,
    val text: String?
)