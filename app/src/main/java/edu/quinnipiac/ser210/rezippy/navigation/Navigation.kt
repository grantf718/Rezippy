/**
 * NavController for handling navigation between screens
 */

package edu.quinnipiac.ser210.rezippy.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.quinnipiac.ser210.rezippy.api.RecipeData.Recipe
import edu.quinnipiac.ser210.rezippy.data.Item
import edu.quinnipiac.ser210.rezippy.model.RecipeViewModel
import edu.quinnipiac.ser210.rezippy.screens.DetailScreen
import edu.quinnipiac.ser210.rezippy.screens.FavoriteScreen
import edu.quinnipiac.ser210.rezippy.screens.HomeScreen
import kotlin.math.max

@Composable
fun Navigation(recipeViewModel: RecipeViewModel){
    // NavController
    val navController = rememberNavController()

    // Access Recipe ViewModel data
    val randomRecipesResult = recipeViewModel.randomRecipesResult.observeAsState()
    val randomRecipesResponse = randomRecipesResult.value    // Random recipes to populate HomeScreen
    val bulkRecipesResult = recipeViewModel.bulkRecipesResult.observeAsState()
    val bulkRecipesResponse = bulkRecipesResult.value        // Bulk recipe requests used by FavoriteScreen

    // Navigation Drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedScreen = remember { mutableStateOf(Screens.HomeScreen.name) }
    val scope = rememberCoroutineScope()

    // Conditional back navigation based on current and previous routes
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Used by FAB for conditional coloring
    var recipeFavorited by remember { mutableStateOf(false) }

    // Used for custom shape FAB
    val hexagon = remember {
        RoundedPolygon.star(
            5,
            rounding = CornerRounding(0.04f)
        )
    }
    val starclip = remember(hexagon) {
        RoundedPolygonShape(polygon = hexagon, rotation = 55f)
    }

    NavDrawer(
        drawerState = drawerState,
        navController = navController,
        scope = scope,
        recipeViewModel = recipeViewModel,
        selectedScreen = selectedScreen
    ) {
        Scaffold (
            topBar = {
                NavBar(
                    navController = navController,
                    scope = scope,
                    drawerState = drawerState,
                    currentRoute = currentRoute
                )
            },
            floatingActionButton = {
                if (currentRoute?.substringBefore("/") == Screens.DetailScreen.name) {
                    var recipe: Recipe? = bulkRecipesResponse?.body()?.firstOrNull { recipe ->
                        recipe.title == backStackEntry?.arguments?.getString("name")
                    }

                    // Used to favorite recipes
                    LargeFloatingActionButton(
                        onClick = {
                            // Add to favorites
                            Log.d("FavoriteCheck", "Recipe $recipe")
                            if(!recipeFavorited) {
                                recipe = randomRecipesResponse?.body()?.recipes?.firstOrNull { recipe ->
                                        recipe.title == backStackEntry?.arguments?.getString("name")
                                    }

                                recipeViewModel.saveRecipe(Item(recipe!!.id))
                                recipeFavorited = true
                                Log.d("FavoriteCheck:", "Saving item with ID: ${recipe?.id}")
                            }
                            // Remove from favorites
                            else {
                                if (recipe != null) {
                                    recipeViewModel.deleteRecipe(Item(recipe!!.id))
                                    recipeFavorited = false
                                    Log.d("FavoriteCheck:", "Deleting item with ID: ${recipe!!.id}")
                                }
                            }
                        },
                        containerColor = if (!recipeFavorited) Color.Gray else Color.Yellow,
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                        modifier = Modifier
                            .clip(starclip)
                    ) {
                        Spacer(modifier = Modifier.size(1.dp))
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.HomeScreen.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(Screens.HomeScreen.name) {
                    HomeScreen(
                        randomRecipes = randomRecipesResponse?.body(),
                        navController = navController
                    )
                }
                composable(
                    Screens.DetailScreen.name+"/{name}",
                    arguments = listOf(navArgument(name = "name") {type = NavType.StringType})
                ) { backStackEntry ->
                    DetailScreen(
                        // Pass only the clicked recipe to the DetailScreen
                        recipe = randomRecipesResponse?.body()?.recipes?.firstOrNull { recipe ->
                            recipe.title == backStackEntry.arguments?.getString("name")
                        } ?: bulkRecipesResponse?.body()?.firstOrNull() { recipe ->
                            recipe.title == backStackEntry.arguments?.getString("name")
                        }
                    )
                }
                composable(Screens.FavoriteScreen.name) {
                    FavoriteScreen(
                        recipeViewModel = recipeViewModel,
                        navController = navController
                    )
                }
                //TODO: Additional Screens(Suggestions/Settings/Help?)
            }
        }
    }
}

// Star shape used for FAB to favorite recipes
fun RoundedPolygon.getBounds() = calculateBounds().let { Rect(it[0], it[1], it[2], it[3]) }
class RoundedPolygonShape(
    private val polygon: RoundedPolygon,
    private var matrix: Matrix = Matrix(),
    private var rotation: Float
) : Shape {
    private var path = Path()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        path.rewind()
        path = polygon.toPath().asComposePath()
        matrix.reset()
        val bounds = polygon.getBounds()
        val maxDimension = max(bounds.width, bounds.height) * 1.05f
        matrix.scale(size.width / maxDimension, size.height / maxDimension)
        matrix.translate(-bounds.left+0.16f, -bounds.top+0.15f)
        matrix.rotateZ(rotation)

        path.transform(matrix)
        return Outline.Generic(path)
    }
}

@Preview
@Composable
fun StarShapePreview() {
    val hexagon = remember {
        RoundedPolygon.star(
            5,
            rounding = CornerRounding(0.04f)
        )
    }
    val clip = remember(hexagon) {
        RoundedPolygonShape(polygon = hexagon, rotation = 55f)
    }
    Box(
        modifier = Modifier
            .clip(clip)
            .background(Color.Transparent)
            .size(80.dp)
    )
}