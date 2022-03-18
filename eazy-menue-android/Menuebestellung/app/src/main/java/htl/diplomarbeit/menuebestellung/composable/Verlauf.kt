package htl.diplomarbeit.menuebestellung.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import htl.diplomarbeit.menuebestellung.bestellungen


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
