package edu.quinnipiac.ser210.rezippy.api.AIData.Requests

data class Parameters(
    val properties: Map<String, Property>,
    val required: List<String>,
    val type: String = "object"
)