package edu.quinnipiac.ser210.rezippy.navigation

enum class Screens {
    HomeScreen,
    DetailScreen,
    FavoriteScreen,
    SettingScreen,
    HelpScreen,
    AIScreen;
    companion object {
        fun fromRoute(route: String?): Screens
                = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            DetailScreen.name -> DetailScreen
            FavoriteScreen.name -> FavoriteScreen
            SettingScreen.name -> SettingScreen
            HelpScreen.name -> HelpScreen
            AIScreen.name -> AIScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route not recognized")
        }
    }

}