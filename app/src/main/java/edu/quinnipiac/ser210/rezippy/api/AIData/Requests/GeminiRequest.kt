package edu.quinnipiac.ser210.rezippy.api.AIData.Requests

data class GeminiRequest(
    val contents: List<RequestContent>,
    val generationConfig: GenerationConfig,
    val systemInstruction: SystemInstruction,
    val tools: List<Tool>?
)