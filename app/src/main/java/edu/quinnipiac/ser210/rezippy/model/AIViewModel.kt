package edu.quinnipiac.ser210.rezippy.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.FunctionDeclaration
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.GeminiRequest
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.GenerationConfig
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.Parameters
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.Property
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.RequestContent
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.SystemInstruction
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.Tool
import edu.quinnipiac.ser210.rezippy.api.AIData.Response.FunctionCall
import edu.quinnipiac.ser210.rezippy.api.AIData.TextPart
import edu.quinnipiac.ser210.rezippy.api.GeminiAPI
import kotlinx.coroutines.launch

class AIViewModel: ViewModel() {
    private val geminiAPI = GeminiAPI.create()

    //Exposes function calls for UI
    private val _functionCall = MutableLiveData<FunctionCall?>()
    val functionCall: LiveData<FunctionCall?> = _functionCall

    //Chat history to provide AI context. Used by UI to build LazyColumn
    private val _chatHistory = MutableLiveData<List<RequestContent>>()
    val chatHistory: LiveData<List<RequestContent>> = _chatHistory

    //System message to give AI direction on how to respond
    private val systemMessage = "You are a friendly and creative cooking assistant who helps users " +
            "find quick, easy, and delicious recipes. Respond in a concise and approachable tone. Limit " +
            "answers to a few sentences. Your primary goal is to suggest meals, recommend recipes based " +
            "on ingredients, and answer cooking questions. If a function is available to fulfill the user’s " +
            "request, prefer calling the function instead of replying with text. If the user seems unsure, " +
            "gently offer simple ideas or ingredient-based suggestions. If you have already called a function, " +
            "chat with the user to get a better idea of what they are looking for."

//            "You are a friendly and creative cooking assistant who helps users " +
//            "find quick, easy, and delicious recipes. Respond in a concise and approachable tone. Limit " +
//            "answers to a few sentences. Your primary goal is to suggest meals, recommend recipes based " +
//            "on ingredients, and answer cooking questions. If a function is available to fulfill the user’s " +
//            "request, prefer calling the function instead of replying with text. **If the user's request for " +
//            "recipe suggestions is vague or lacks specific ingredients, instead of directly asking the user, " +
//            "generate a short, comma-separated list of common or complementary ingredients that could be used " +
//            "to find recipes (e.g., 'chicken, broccoli, rice').** If the user seems unsure, gently offer simple " +
//            "ideas or ingredient-based suggestions."

//            "You are a friendly and helpful cooking assistant who prefers to take action. Respond concisely. " +
//            "Limit answers to a few sentences. Your primary goal is to suggest meals, recommend recipes based " +
//            "on ingredients, and answer cooking questions. **Whenever possible, if a function is available to " +
//            "address the user's request, prioritize calling that function over asking for more information.** " +
//            "If the user provides a vague request for recipes without specific ingredients, proactively suggest " +
//            "a few common ingredients in a comma-separated format (e.g., 'beef, onions, carrots') that could be " +
//            "used to search for recipes. Only ask for clarification as a last resort if you absolutely cannot " +
//            "proceed without more information and no suitable function can be called."

    //Fetch gemini response
    fun fetchResponse(message: String) {
        viewModelScope.launch {
            try {
                // Update chatHistory with user prompt
                addChatHistory(role = "user", message = message)

                //Construct request body from prompt
                val request = GeminiRequest(
                    contents = _chatHistory.value.orEmpty(),
                    systemInstruction = SystemInstruction(
                        parts = listOf(TextPart(text = systemMessage))
                    ),
                    generationConfig = GenerationConfig(
                        candidateCount = 1,             //Number of potential response candidates returned
                        maxOutputTokens = 300,          //Used to limit size of response
                        stopSequences = listOf("\n\n"), //Used to create a stop point if a specific string is generated
                        temperature = 0.4               //Randomness of response, 0 is low, 2 is high
                    ),
                    tools = listOf(Tool(
                        function_declarations = listOf(
                            FunctionDeclaration(
                                description = "Searches for recipe by list of ingredients",
                                name = "search_recipes_by_ingredients",
                                parameters = Parameters(
                                    properties = mapOf(
                                        "ingredients" to Property(
                                            type = "string",
                                            description = "Comma-separated ingredients, e.g. 'eggs,spinach,feta'"
                                        )
                                    ),
                                    required = listOf("ingredients")
                                ),
                            )
                        )
                    ))
                )

                val response = geminiAPI.generateContent(request = request)

                if (response.isSuccessful) {
                    Log.d("Gemini", "AI Response Success")

                    val candidate = response.body()?.candidates?.get(0)
                    val currentFunction = candidate?.content?.parts?.get(0)?.functionCall

                    // Update chat history with AI response
                    if (currentFunction != null) {
                        Log.d("Gemini", "Function Call (finishReason: ${candidate.finishReason}) ${currentFunction.name} params: ${currentFunction.args}")
                        addChatHistory(
                            role = "model",
                            message = "*Function call: ${currentFunction.name}*"
                        )

                        _functionCall.value = currentFunction
                    }
                    else {
                        addChatHistory(
                            role = "model",
                            message = candidate?.content?.parts?.get(0)?.text ?: "No response"
                        )
                    }
                }
                else {
                    Log.e("Gemini", "API error: ${response.code()}: ${response.errorBody()?.string()}")
                    addChatHistory(
                        role = "model",
                        message = "Error fetching response."
                    )
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("Gemini Network Error", it) }
                addChatHistory(
                    role = "model",
                    message = "Error fetching response."
                )
            }
        }
    }

    fun addChatHistory(role: String = "user", message: String) {
        val currentChat = _chatHistory.value.orEmpty().toMutableList()
        currentChat.add(
            RequestContent(
                role = role,
                parts = listOf(TextPart(text = message))
            )
        )

        // Limit chatHistory for token usage
        if (currentChat.size > 10) {
            currentChat.removeAt(0)    //Remove oldest
        }

        _chatHistory.value = currentChat
    }

    fun clearFunctionCall() {
        _functionCall.value = null
    }
}