package com.example.menuebestellung.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.menuebestellung.*
import com.example.menuebestellung.dataClasses.Menue

@Composable
fun CardDemo(menue: Menue, navController: NavHostController) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.width(530.dp)
    ) {
        Box(modifier = Modifier.height(50.dp)){
            Text(
                text = menue.code + ':',
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(12.dp)
            )
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){

                Button(enabled = !menueIsInThePast.value,
                    onClick = {
                        if (isLoggedIn.value) {
                            bestellungMenueId.value = menue.id
                            bestellungMenue.value = menue.mainDish
                            bestellungDate.value = menue.date
                            bestellungErsteller.value = currUser.value
                            bestellungFuer.value = currUser.value
                            navController.navigate("bestellen")
                        }
                    }) {
                    Text(text = menue.mainDish)
                }
            }
        }



    }




}

@Composable
fun CardDemoDessertAppertizer(
    dessertOrAppetizer: String,
    navController: NavHostController,
    dessertOrAppetizerBoolean: Boolean
) {

    var dessertOrAppetizerTemp = ""

    if (dessertOrAppetizerBoolean) {
        dessertOrAppetizerTemp = "Suppe:"
    } else {
        dessertOrAppetizerTemp = "Dessert:"
    }
    Card(
        elevation = 4.dp,
        modifier = Modifier.width(530.dp),
        backgroundColor = Color.LightGray
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .padding(start = 3.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            Text(
                text = dessertOrAppetizerTemp,
                fontSize = 20.sp,
                color = Color.Gray
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dessertOrAppetizer,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

        }


    }
}