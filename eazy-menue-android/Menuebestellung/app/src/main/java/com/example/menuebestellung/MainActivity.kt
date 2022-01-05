package com.example.menuebestellung

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.menuebestellung.ui.theme.MenuebestellungTheme
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.util.*
import okhttp3.*
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime


class MainActivity : ComponentActivity() {


    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MenuebestellungTheme {


                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {

                        if (isLoggedIn.value) {
                                BottomNavigationBar(
                                    items = listOf(

                                        BottomNavItem(
                                            name = "Login",
                                            route = "login",
                                            icon = Icons.Default.AccountCircle
                                        ),
                                        BottomNavItem(
                                            name = "Uebersicht",
                                            route = "uebersicht",
                                            icon = Icons.Default.Info
                                        ),
                                        BottomNavItem(
                                            name = "Verlauf",
                                            route = "verlauf",
                                            icon = Icons.Default.List
                                        ),
                                        BottomNavItem(
                                            name = "Stats",
                                            route = "stats",
                                            icon = Icons.Default.Settings
                                        )
                                    ),
                                    navController = navController,
                                    onItemClick = {
                                        navController.navigate(it.route)
                                    }
                                )
                        }
                    }
                ) {
                    Navigation(navController = navController)
                }
            }

        }

    }

}


@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("uebersicht") {

            UebersichScreen(navController)
        }
        composable("verlauf") {
            VerlaufScreen(navController)
        }
        composable("stats") {
            StatsScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("bestellen") {
            BestellenScreen(navController)
        }
    }
}

var uebersichtDate = mutableStateOf("")
var menueIsInThePast = mutableStateOf(false)

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun UebersichScreen(navController: NavHostController) {

    DatePicker(context = LocalContext.current, navController)

    var appetizer by remember {
        mutableStateOf("")
    }

    var dessert by remember {
        mutableStateOf("")
    }

    Text(text = "Suppe: " + appetizer, modifier = Modifier.paddingFromBaseline(140.dp))

    LazyColumn(verticalArrangement = Arrangement.Top, modifier = Modifier.padding(80.dp, 140.dp)) {
        items(menuesFilteredByDate.toMutableStateList()) { menue ->
            appetizer = menue.appetizer
            dessert = menue.dessert

            CardDemo(menue, navController)
        }
    }

    Text(text = "Dessert: " + dessert, modifier = Modifier.paddingFromBaseline(540.dp))


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(context: Context, navController: NavHostController) {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                var temp = month.inc()
                if (temp.toString().length == 1) {
                    date.value = "$year-0${temp}-0$dayOfMonth"
                }
                else{
                    date.value = "$year-${temp}-0$dayOfMonth"
                }
            } else {
                if (month.toString().length == 1) {
                    date.value = "$year-0${month}-0$dayOfMonth"
                }
                else{
                    date.value = "$year-${month}-0$dayOfMonth"
                }
            }
            getMenuesForDate(date.value)
            uebersichtDate.value = date.value
            navController.navigate("uebersicht")
        }, year, month, day
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                datePickerDialog.show()
            },
            enabled = isLoggedIn.value
        ) {
            Text(text = "Open Date Picker")
        }
        Text(
            text = "Selected Date: ${uebersichtDate.value}"
        )

    }
}

@Composable
fun CardDemo(menue: Menue, navController: NavHostController) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.width(530.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Text(
                        text = menue.code,
                        fontSize = 20.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(12.dp)
                    )

                    Text(
                        text = menue.mainDish,
                        fontSize = 20.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Button(enabled = !menueIsInThePast.value,
                    onClick = {
                    if (isLoggedIn.value) {
                        navController.navigate("bestellen")
                    }
                }) {
                    Text(text = "Bestellen")
                }
            }
        }
    }
    bestellungMenue.value = menue.mainDish
    bestellungDate.value = menue.date
    bestellungErsteller.value = currUser.value
}


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

@Composable
fun BestellungItem(bestellung: Bestellung) {

    var mainDish = ""
    var mainDishDate = ""
    menues.forEach { menue ->
        if (menue.date == bestellung.menueDate) {
            mainDish = menue.mainDish
            mainDishDate = bestellung.createdAt
        }
    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = bestellung.menueName,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = bestellung.createdAt.take(10),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = bestellung.menueDate,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(12.dp)
        )

    }

}


var oeffnungszeitenChecked = mutableStateOf(false)

@Composable
fun OeffnungszeitenItem(oeffnungszeiten: Oeffnungszeiten) {


    val checkedState = remember { mutableStateOf(oeffnungszeiten.chosen) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = oeffnungszeiten.time,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(12.dp)
        )
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
            }
        )
        Text(
            text = oeffnungszeiten.maxSeats.toString(),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(12.dp)

        )

    }

}


@Composable
fun StatsScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Stats screen")
    }
}


var isLoggedIn = mutableStateOf(false)
var currUser = mutableStateOf("");

@Composable
fun LoginScreen(navController: NavHostController) {

    getOeffnungszeiten()
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    if (!isLoggedIn.value) {
        Column() {
            Row() {

                TextField(value = name, onValueChange = {
                    name = it
                    currUser.value = name
                })
            }
            Row() {
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Row() {


                Button(onClick = {
                    if (!name.isEmpty() && !password.isEmpty()) {
                        InitMenues()
                        InitBestellungen(name)
                        Toast.makeText(
                            context,
                            "Logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        isLoggedIn.value = true
                    }
                }) {
                    Text(text = "Sign In")
                }
            }
        }
    } else {
        Button(onClick = {
            currUser.value = ""
            menuesFilteredByDate = menuesFilteredByDate - menuesFilteredByDate
            InitMenues()
            InitBestellungen("")
            Toast.makeText(
                context,
                "Logged out successfully",
                Toast.LENGTH_SHORT
            ).show()
            isLoggedIn.value = false
        }) {
            Text(text = "Sign out")
        }
    }

}


var bestellungErsteller = mutableStateOf("")
var bestellungDate = mutableStateOf("")
var bestellungMenue = mutableStateOf("")
var bestellungComment = mutableStateOf("")
var bestellungCount = mutableStateOf(1)
var isOne = mutableStateOf(true)

@Composable
fun BestellenScreen(navController: NavHostController) {


    Column() {
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

@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgeBox(
                                badgeContent = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}


val client = OkHttpClient()
var menues: Collection<Menue> = mutableStateListOf<Menue>()
var menuesFilteredByDate: Collection<Menue> = mutableStateListOf<Menue>()
var bestellungen: Collection<Bestellung> = mutableStateListOf<Bestellung>()
var oeffnungszeiten: Collection<Oeffnungszeiten> = mutableStateListOf<Oeffnungszeiten>()

fun InitMenues(): Collection<Menue> {

    val url = "http://10.0.2.2:8080/menue/menues"

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                println("-----------------------------------------------------------------------")
                println("-----------------------------------------------------------------------")
                println("-----------------------------------------------------------------------")
                println("-----------------------------------------------------------------------")


                val gson = GsonBuilder().create()

                val collectionType: Type =
                    object : TypeToken<Collection<Menue?>?>() {}.type
                menues = gson.fromJson(response.body!!.string(), collectionType)
            }
        }
    })

    return listOf<Menue>()
}

fun InitBestellungen(currUser: String): Collection<Bestellung> {

    if (currUser == "") {
        bestellungen = bestellungen - bestellungen
    } else {

        val url = "http://10.0.2.2:8080/menue/bestellung/" + currUser

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    println("-----------------------------------------------------------------------")
                    println("----------------Bestellung---------------------------------------------")
                    println("------------------------------------------------------------")


                    val gson = GsonBuilder().create()

                    val collectionType: Type =
                        object : TypeToken<Collection<Bestellung?>?>() {}.type
                    bestellungen = gson.fromJson(response.body!!.string(), collectionType)
                }
            }
        })

    }

    return listOf<Bestellung>()
}

fun <T> SnapshotStateList<T>.swapList(newList: Collection<T>) {
    clear()
    addAll(newList)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getMenuesForDate(date: String) {
    var i = LocalDateTime.now().hour
    if (date <= LocalDateTime.now().toString().take(10) && LocalDateTime.now().hour < 9) {
        menueIsInThePast.value = true
    }
    menuesFilteredByDate = menuesFilteredByDate - menuesFilteredByDate
    menues.sortedBy { menue -> menue.date }.sortedBy { menue -> menue.code }.forEach { menu ->
        if (menu.date == date) {
            menuesFilteredByDate = menuesFilteredByDate.plusElement(menu)

        }

    }

}

fun getOeffnungszeiten() {
    val url = "http://10.0.2.2:8080/menue/oeffnungszeiten"

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                println("-----------------------------------------------------------------------")
                println("-----------------------------------------------------------------------")
                println("------------------------Oeffnungszeiten--------------------------")
                println("-----------------------------------------------------------------------")


                val gson = GsonBuilder().create()

                val collectionType: Type =
                    object : TypeToken<Collection<Oeffnungszeiten?>?>() {}.type
                oeffnungszeiten = gson.fromJson(response.body!!.string(), collectionType)
            }
        }
    })
}