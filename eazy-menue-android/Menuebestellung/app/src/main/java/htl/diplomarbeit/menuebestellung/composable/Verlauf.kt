package htl.diplomarbeit.menuebestellung.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import htl.diplomarbeit.menuebestellung.api.*


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
