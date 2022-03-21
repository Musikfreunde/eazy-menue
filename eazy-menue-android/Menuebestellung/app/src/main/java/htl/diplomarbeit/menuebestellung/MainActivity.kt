package htl.diplomarbeit.menuebestellung

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import htl.diplomarbeit.menuebestellung.composable.BottomNavigationBar
import htl.diplomarbeit.menuebestellung.composable.Navigation
import htl.diplomarbeit.menuebestellung.composable.isLoggedIn
import htl.diplomarbeit.menuebestellung.composable.onBestellScreen
import htl.diplomarbeit.menuebestellung.dataClasses.BottomNavItem
import htl.diplomarbeit.menuebestellung.ui.theme.MenuebestellungTheme

class MainActivity : ComponentActivity() {


    @ExperimentalComposeUiApi
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MenuebestellungTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {

                        if (isLoggedIn.value && !onBestellScreen.value) {
                            BottomNavigationBar(
                                items = listOf(

                                    BottomNavItem(
                                        name = "Login",
                                        route = "login",
                                        icon = Icons.Default.AccountCircle
                                    ),
                                    BottomNavItem(
                                        name = "Uebersicht",
                                        route = "uebersicht",
                                        icon = Icons.Default.Info
                                    ),
                                    BottomNavItem(
                                        name = "Verlauf",
                                        route = "verlauf",
                                        icon = Icons.Default.List
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation(navController = navController)
                    }
                }
            }

        }

    }

}


