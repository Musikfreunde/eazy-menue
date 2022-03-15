package com.example.menuebestellung.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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


    onBestellScreen.value = false

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            horizontalAlignment = Alignment.Start
        ) {
            items(bestellungen.toMutableStateList()) { bestellung ->

                BestellungItem(bestellung = bestellung)
            }

        }
    }
}
