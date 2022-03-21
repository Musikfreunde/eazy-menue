package htl.diplomarbeit.menuebestellung.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import htl.diplomarbeit.menuebestellung.api.*

var uebersichtDate = mutableStateOf("")
var menueIsInThePast = mutableStateOf(false)

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun UebersichScreen(navController: NavHostController) {


    onBestellScreen.value = false

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

        Card(
            modifier = Modifier
                // The space between each card and the other
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .wrapContentSize(Alignment.Center)
                .background(Color(4)),
            shape = MaterialTheme.shapes.medium,
            elevation = 10.dp,
            backgroundColor = MaterialTheme.colors.primary
        ) {
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


        Card(
            modifier = Modifier
                // The space between each card and the other
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .wrapContentSize(Alignment.Center)
                .background(Color(4)),
            shape = MaterialTheme.shapes.medium,
            elevation = 10.dp,
            backgroundColor = MaterialTheme.colors.primary
        ) {
            CardDemoDessertAppertizer(dessert, navController, false)

        }

    }
}