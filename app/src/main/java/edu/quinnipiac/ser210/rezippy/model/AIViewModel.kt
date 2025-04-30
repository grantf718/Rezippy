package edu.quinnipiac.ser210.rezippy.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.rezippy.api.AIData.Content
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.GeminiRequest
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.GenerationConfig
import edu.quinnipiac.ser210.rezippy.api.AIData.Part
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.SystemInstruction
import edu.quinnipiac.ser210.rezippy.api.AIData.Response.GeminiResponse
import edu.quinnipiac.ser210.rezippy.api.GeminiAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class AIViewModel: ViewModel() {
    private val geminiAPI = GeminiAPI.create()

    //LiveData for AI response content
    private val _aiResponse = MutableLiveData<Response<GeminiResponse>>()
    val aiResponse: LiveData<Response<GeminiResponse>> = _aiResponse

    //Chat history to provide AI context
    private val _chatHistory = MutableLiveData<List<Content>>()

    //System message to give AI direction on how to respond
    private val systemMessage = "You are a friendly and creative cooking assistant who helps users " +
            "find quick, easy, and delicious recipes. Respond in a concise and approachable tone. Limit " +
            "answers to a few sentences. Your primary goal is to suggest meals, recommend recipes based " +
            "on ingredients, and answer cooking questions. If a function is available to fulfill the user’s " +
            "request, prefer calling the function instead of replying with text. If the user seems unsure, " +
            "gently offer simple ideas or ingredient-based suggestions."

    //Fetch gemini response
    fun fetchResponse(message: String) {
        viewModelScope.launch {
            try {
                // Update chatHistory with user prompt
                addChatHistory(role = "user", message = message)

                //Construct request body from prompt
                val request = GeminiRequest(
                    contents = _chatHistory.value,
                    systemInstruction = SystemInstruction(
                        parts = listOf(Part(text = systemMessage))
                    ),
                    generationConfig = GenerationConfig(
                        candidateCount = 1,    //Number of potential response candidates returned
                        maxOutputTokens = 250, //Used to limit size of response
                        stopSequences = null,  //Used to create a stop point if a specific string is generated
                        temperature = 0.4      //Randomness of response, 0 is low, 2 is high
                    ),
                    //TODO: function calling from tools
                    tools = null
                )

                val response = geminiAPI.generateContent(request = request)

                if (response.isSuccessful) {
                    Log.d("Gemini", "AI Response Success")
                    _aiResponse.value = response

                    //TODO: do the function calling

                    // Update chat history with AI response
                    addChatHistory(
                        role = "model",
                        message = _aiResponse.value.body()?.candidates?.get(0)?.content?.parts?.get(0)?.text
                            ?: "*Called function*"
                    )
                }
                else {
                    Log.e("Gemini", "API error: ${response.code()}")
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("Gemini Network Error", it) }
            }
        }
    }

    fun addChatHistory(role: String, message: String) {
        val currentChat = _chatHistory.value.orEmpty().toMutableList()
        currentChat.add(
            Content(
                role = role,
                parts = listOf(Part(text = message))
            )
        )

        // Limit chatHistory for token usage
        if (currentChat.size > 10) {
            currentChat.removeAt(0)    //Remove oldest
        }

        _chatHistory.value = currentChat
    }
}