package edu.quinnipiac.ser210.rezippy.screens

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.quinnipiac.ser210.rezippy.api.AIData.MessageItem
import edu.quinnipiac.ser210.rezippy.model.AIViewModel
import edu.quinnipiac.ser210.rezippy.model.RecipeViewModel
import edu.quinnipiac.ser210.rezippy.navigation.Screens
import edu.quinnipiac.ser210.rezippy.ui.components.RecipeCard
import edu.quinnipiac.ser210.rezippy.ui.theme.AppTheme
import edu.quinnipiac.ser210.rezippy.ui.theme.bodyFontFamily

@Composable
fun AIScreen(
    aiViewModel: AIViewModel,
    recipeViewModel: RecipeViewModel,
    navController: NavController
){
    // AI ViewModel values
    val chatHistory by aiViewModel.chatHistory.observeAsState()
    val functionCall by aiViewModel.functionCall.observeAsState()

    LaunchedEffect (functionCall) {
        functionCall?.let { call ->
            if (call.name == "search_recipes_by_ingredients") {
                recipeViewModel.searchRecipesByIngredients(call.args["ingredients"] ?: "")
                aiViewModel.clearFunctionCall()
            }
        }
    }

    // Recipe ViewModel values
    val recipesByIngredient by recipeViewModel.recipesByIngredient.observeAsState()

    // User input
    val inputText = remember { mutableStateOf("") }

    // Set initial chat message
    LaunchedEffect (chatHistory) {
        if (chatHistory.isNullOrEmpty()) {
            aiViewModel.addChatHistory(
                role = "model",
                message = "I'm Rezippy AI! Ask me for recipe suggestions based on the ingredients in your fridge!"
            )
            // Double check functionCalls is empty
            aiViewModel.clearFunctionCall()
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            // Scrollable list of messages
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .weight(9f)
            ){
                var functionIndex = 0
                items(chatHistory ?: emptyList()) { msg ->
                    // Check for function call to display RecipeCard
                    if (msg.parts[0].text.startsWith("*Function call:")) {
                        // Include a message and a RecipeCard to navigate to detail page
                        Message(
                            sentByUser = false,
                            messageText = "Check out this recipe!"
                        )

                        // Include RecipeCard
                        val recipe = recipesByIngredient?.get(functionIndex)
                        if (recipe != null) {
                            RecipeCard(
                                recipe = recipe,
                                modifier = Modifier.padding(end = 64.dp, top = 10.dp, bottom = 10.dp)
                            ) {
                                navController.navigate(route = Screens.DetailScreen.name+"/${recipe.title}")
                            }
                            functionIndex++
                        }
                    }
                    else {
                        Message(
                            sentByUser = msg.role == "user",
                            messageText = msg.parts[0].text
                        )
                    }
                }
            }

            // Send message area
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){

                val keyboardController = LocalSoftwareKeyboardController.current
                val sendTypedUserMessage = {
                    val message = inputText.value.trim()
                    if (message.isNotEmpty()) {
                        aiViewModel.fetchResponse(message = message)
                        inputText.value = "" // clear input
                        keyboardController?.hide()
                    }

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ){
                    // Text input
                    TextField(
                        value = inputText.value,
                        onValueChange = { inputText.value = it },
                        shape = RoundedCornerShape(99.dp),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Type to Rezippy AI",
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Default
                            )
                        },
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Default,
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(5f)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(99.dp)
                            ),
                        // Handle pressing enter
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                sendTypedUserMessage()
                            }
                        )
                    )
                    // Send button
                    IconButton(
                        onClick = { sendTypedUserMessage() },
                        enabled = true,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(99.dp)
                            ),
                        ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Message(
    sentByUser: Boolean,
    messageText: String
){

    // Determine color of message bubble
    val bubbleColor = if(sentByUser){
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.tertiary
    }

    // Alignment to left or right of screen
    val alignment = if(sentByUser) Alignment.CenterEnd else Alignment.CenterStart

    // Padding on the opposite side of the message
    val horizontalPadding = if (sentByUser) {
        Modifier.padding(start = 64.dp)
    } else {
        Modifier.padding(end = 64.dp)
    }


    Box(
        contentAlignment = alignment,
        modifier = Modifier
            .fillMaxWidth()
            .then(horizontalPadding) // Space on one side
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = bubbleColor,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
        ){
            Text(
                messageText,
                fontFamily = FontFamily.Default,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
fun AIPreview(){
    AppTheme(){
//        AIScreen()
    }
}