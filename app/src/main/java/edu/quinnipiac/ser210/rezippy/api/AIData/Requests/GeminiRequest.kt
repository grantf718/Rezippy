package edu.quinnipiac.ser210.rezippy.api.AIData.Requests

import edu.quinnipiac.ser210.rezippy.api.AIData.Content

data class GeminiRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig,
    val systemInstruction: SystemInstruction,
    val tools: List<Tool>?
)