package htl.diplomarbeit.menuebestellung.composable

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import htl.diplomarbeit.menuebestellung.api.oeffnungszeiten
import htl.diplomarbeit.menuebestellung.api.postBestellung


var bestellungErsteller = mutableStateOf("")
var bestellungFuer = mutableStateOf("")
var bestellungDate = mutableStateOf("")
var bestellungMenue = mutableStateOf("")
var bestellungComment = mutableStateOf("")
var bestellungCount = mutableStateOf(1)
var bestellungMenueId = mutableStateOf(0)
var bestellungZeitId = mutableStateOf(6) //Temporär
var isOne = mutableStateOf(true)
var onBestellScreen = mutableStateOf(false)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BestellenScreen(navController: NavHostController) {
    onBestellScreen.value = true

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {

                var ersteller by remember { mutableStateOf(bestellungErsteller.value) }

                TextField(
                    value = ersteller,
                    onValueChange = { ersteller = it },
                    enabled = false,
                    label = { Text("Ersteller") },
                    shape = RoundedCornerShape(8.dp)
                )

            }
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {

                var date by remember { mutableStateOf(bestellungDate.value) }

                TextField(
                    value = date,
                    onValueChange = { date = it },
                    enabled = false,
                    label = { Text("Date") },
                    shape = RoundedCornerShape(8.dp)
                )

            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {

                var menue by remember { mutableStateOf(bestellungMenue.value) }

                TextField(
                    value = menue,
                    onValueChange = { menue = it },
                    enabled = false,
                    label = { Text("Menue") },
                    shape = RoundedCornerShape(8.dp)
                )

            }
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Button(
                            enabled = !isOne.value,
                            onClick = { bestellungCount.value = bestellungCount.value - 1 }) {
                            Text(text = "-")
                            if (bestellungCount.value == 1)
                                isOne.value = true;
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(3f)
                    ) {

                        var count by remember { mutableStateOf(bestellungCount.value) }

                        TextField(
                            enabled = false,
                            value = bestellungCount.value.toString(),
                            onValueChange = { bestellungCount.value = count },
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center
                            ),
                            shape = RoundedCornerShape(8.dp),
                            label = { Text("Anzahl") }
                        )
                    }
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Button(onClick = { bestellungCount.value = bestellungCount.value + 1 }) {
                            Text(text = "+")
                            if (bestellungCount.value != 1)
                                isOne.value = false;
                        }
                    }
                }

            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {

                var orderedFor by remember { mutableStateOf(bestellungFuer.value) }
                TextField(
                    value = orderedFor,
                    onValueChange = { orderedFor = it
                                    bestellungFuer.value = orderedFor},
                    label = { Text("Für") },
                    shape = RoundedCornerShape(8.dp)
                )

            }
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {

                var comment by remember { mutableStateOf(bestellungComment.value) }

                TextField(
                    value = comment,
                    onValueChange = { comment = it
                                    bestellungComment.value = comment},
                    label = { Text("Kommentar") },
                    shape = RoundedCornerShape(8.dp)
                )

            }
        }







        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .border(2.dp, Color.Black)
                    .wrapContentSize()

            ) {
                LazyColumn(
                    modifier = Modifier.padding(top = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    items(oeffnungszeiten.toMutableStateList()) { oeffnungszeiten ->
                        OeffnungszeitenItem(oeffnungszeiten = oeffnungszeiten, bestellungDate.value)
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {
                Button(
                    onClick = {
                        navController.navigate("uebersicht")
                        postBestellung()
                        isLoggedIn.value = true
                        Toast.makeText(
                            context,
                            "Bestellung erfolgreich",
                            Toast.LENGTH_SHORT
                        ).show()

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                ) {
                    Text(text = "Abschließen")

                }
            }
            Surface(
                modifier = Modifier
                    .weight(1f)

            ) {
                Button(
                    onClick = {
                        bestellungCount.value = 1
                        navController.navigate("uebersicht")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(text = "Abbrechen")
                }
            }
        }
    }
}



