package com.example.menuebestellung.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.menuebestellung.oeffnungszeiten
import com.example.menuebestellung.postBestellung
import androidx.compose.foundation.layout.Arrangement





var bestellungErsteller = mutableStateOf("")
var bestellungFuer = mutableStateOf("")
var bestellungDate = mutableStateOf("")
var bestellungMenue = mutableStateOf("")
var bestellungComment = mutableStateOf("")
var bestellungCount = mutableStateOf(1)
var bestellungMenueId = mutableStateOf(0)
var bestellungZeitId = mutableStateOf(6) //Temporär
var isOne = mutableStateOf(true)
@Composable
fun BestellenScreen(navController: NavHostController) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row() {
            var ersteller by remember { mutableStateOf(bestellungErsteller.value) }

            TextField(
                value = ersteller,
                onValueChange = { ersteller = it },
                label = { Text("Ersteller") }
            )
        }
        Row() {
            var date by remember { mutableStateOf(bestellungDate.value) }

            TextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") }
            )
        }
        Row() {
            var menue by remember { mutableStateOf(bestellungMenue.value) }

            TextField(
                value = menue,
                onValueChange = { menue = it },
                label = { Text("Menue") }
            )
        }
        Row() {
            Button(
                enabled = !isOne.value,
                onClick = { bestellungCount.value = bestellungCount.value - 1 }) {
                Text(text = "-")
                if (bestellungCount.value == 1)
                    isOne.value = true;
            }
            var count by remember { mutableStateOf(bestellungCount.value) }

            TextField(
                value = bestellungCount.value.toString(),
                onValueChange = { bestellungCount.value = count },
                modifier = Modifier.width(90.dp)
            )

            Button(onClick = { bestellungCount.value = bestellungCount.value + 1 }) {
                Text(text = "+")
                if (bestellungCount.value != 1)
                    isOne.value = false;
            }
        }
        Row() {
            var orderedFor by remember { mutableStateOf(bestellungFuer.value) }

            TextField(
                value = orderedFor,
                onValueChange = { orderedFor = it },
                label = { Text("Für") }
            )
        }
        Row() {
            var comment by remember { mutableStateOf(bestellungComment.value) }

            TextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Kommentar") }
            )
        }
        Row() {
            Button(
                onClick = {
                    navController.navigate("uebersicht")
                    postBestellung()
                    isLoggedIn.value = true
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(text = "Abschließen")
            }
            Button(
                onClick = {
                    navController.navigate("uebersicht")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(text = "Abbrechen")
            }
        }
        Row() {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(top=2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Zeit",
                                fontSize = 25.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(12.dp)
                            )
                            Text(
                                text = "Auswahl",
                                fontSize = 25.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(12.dp)
                            )
                            Text(
                                text = "Freie Plätze",
                                fontSize = 25.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(12.dp)
                            )

                        }

                    }
                    items(oeffnungszeiten.toMutableStateList()) { oeffnungszeiten ->
                        OeffnungszeitenItem(oeffnungszeiten = oeffnungszeiten)
                    }
                }
            }
        }
    }

}

