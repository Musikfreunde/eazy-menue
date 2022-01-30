package com.example.menuebestellung.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.menuebestellung.DatePicker
import com.example.menuebestellung.menuesFilteredByDate

var uebersichtDate = mutableStateOf("")
var menueIsInThePast = mutableStateOf(false)

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun UebersichScreen(navController: NavHostController) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row() {


            DatePicker(context = LocalContext.current, navController)
        }
        var appetizer by remember {
            mutableStateOf("")
        }

        var dessert by remember {
            mutableStateOf("")
        }
        Row() {


            CardDemoDessertAppertizer(appetizer, navController, true)

        }
        Row() {


            LazyColumn(
                verticalArrangement = Arrangement.Top
            ) {
                items(menuesFilteredByDate.toMutableStateList()) { menue ->
                    appetizer = menue.appetizer
                    dessert = menue.dessert

                    CardDemo(menue, navController)
                }
            }
        }
        Row() {


            CardDemoDessertAppertizer(dessert, navController, false)
        }
    }
}