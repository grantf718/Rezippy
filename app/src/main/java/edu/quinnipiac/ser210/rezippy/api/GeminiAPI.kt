package edu.quinnipiac.ser210.rezippy.api

import edu.quinnipiac.ser210.rezippy.BuildConfig
import edu.quinnipiac.ser210.rezippy.api.AIData.Requests.GeminiRequest
import edu.quinnipiac.ser210.rezippy.api.AIData.Response.GeminiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiAPI {
    // Method to generate AI response
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String = BuildConfig.GEMINI_API_KEY,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>

    companion object {
        const val BASE_URL = "https://generativelanguage.googleapis.com/"

        fun create() : GeminiAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GeminiAPI::class.java)

        }
    }
}