package edu.quinnipiac.ser210.rezippy.api.AIData.Requests

data class Parameters(
    val properties: Properties,
    val required: List<String>,
    val type: String = "object"
)