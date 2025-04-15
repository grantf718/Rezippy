package edu.quinnipiac.ser210.rezippy.navigation

enum class Screens {
    HomeScreen,
    DetailScreen;
    companion object {
        fun fromRoute(route: String?): Screens
                = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            DetailScreen.name -> DetailScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route not recognized")
        }
    }

}