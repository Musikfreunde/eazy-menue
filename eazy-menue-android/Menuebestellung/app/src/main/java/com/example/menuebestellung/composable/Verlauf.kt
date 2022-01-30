package com.example.menuebestellung.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.menuebestellung.bestellungen

@Composable
fun VerlaufScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn() {
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Menu",
                        fontSize = 25.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = "Ordered Date",
                        fontSize = 25.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = "Menue Date",
                        fontSize = 25.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(12.dp)
                    )

                }

            }
            items(bestellungen.toMutableStateList()) { bestellung ->
                BestellungItem(bestellung = bestellung)
            }
        }
    }
}
