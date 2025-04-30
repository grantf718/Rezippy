package edu.quinnipiac.ser210.rezippy.api.AIData.Response

data class FunctionCall(
    val args: Args,
    val name: String
)