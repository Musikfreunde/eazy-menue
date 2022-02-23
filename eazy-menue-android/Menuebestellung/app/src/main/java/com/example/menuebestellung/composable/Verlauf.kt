package com.example.menuebestellung.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.menuebestellung.bestellungen
import com.example.menuebestellung.dataClasses.Bestellung
import com.example.menuebestellung.getBestellungen


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun VerlaufScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn() {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color.Black)
                ) {
                    Column() {
                        Text(
                            text = "Menu",
                            fontSize = 25.sp,
                            color = Color.White,
                            modifier = Modifier.padding(12.dp)

                        )
                    }
                    Column() {
                        Text(
                            text = "Ordered Date",
                            fontSize = 25.sp,
                            color = Color.White,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Column() {
                        Text(
                            text = "Menue Date",
                            fontSize = 25.sp,
                            color = Color.White,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                }

            }
            items(bestellungen.toMutableStateList()) { bestellung ->
                BestellungItem(bestellung = bestellung)
            }
        }
    }
}
