package edu.quinnipiac.ser210.rezippy.api.AIData.Requests

import edu.quinnipiac.ser210.rezippy.api.AIData.Part

data class SystemInstruction(
    val parts: List<Part>
)