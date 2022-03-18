package com.diplomarbeit.menuebestellung

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import htl.diplomarbeit.menuebestellung.composable.BestellenScreen
import htl.diplomarbeit.menuebestellung.composable.LoginScreen
import htl.diplomarbeit.menuebestellung.composable.UebersichScreen
import htl.diplomarbeit.menuebestellung.composable.VerlaufScreen


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("uebersicht") {

            UebersichScreen(navController)
        }
        composable("verlauf") {
            VerlaufScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("bestellen") {
            BestellenScreen(navController)
        }
    }
}