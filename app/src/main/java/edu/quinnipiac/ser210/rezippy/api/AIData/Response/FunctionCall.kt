package edu.quinnipiac.ser210.rezippy.api.AIData.Response

data class FunctionCall(
    val args: Map<String, String>,
    val name: String
)