package com.example.menuebestellung

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.menuebestellung.composable.*
import com.example.menuebestellung.dataClasses.Bestellung
import com.example.menuebestellung.dataClasses.BottomNavItem
import com.example.menuebestellung.dataClasses.Menue
import com.example.menuebestellung.dataClasses.Oeffnungszeiten
import com.example.menuebestellung.ui.theme.MenuebestellungTheme
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch


class MainActivity : ComponentActivity() {


    @ExperimentalComposeUiApi
    @RequiresApi(Build.VERSION_CODES.O)
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
                    innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation(navController = navController)
                }
                }
            }

        }

    }

}


val client = OkHttpClient()
var menues: Collection<Menue> = mutableStateListOf<Menue>()
var menuesFilteredByDate: Collection<Menue> = mutableStateListOf<Menue>()
var bestellungen: Collection<Bestellung> = mutableStateListOf<Bestellung>()
var oeffnungszeiten: Collection<Oeffnungszeiten> = mutableStateListOf<Oeffnungszeiten>()



@JvmName("getBestellungen1")
fun getBestellungen(): Collection<Bestellung> {
        return bestellungen;
}



fun InitMenues(): Collection<Menue> {

    val url = "http://10.0.2.2:8080/menue/menues"

    val request = Request.Builder()
        .url(url)
        .get()
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
    if (date <= LocalDateTime.now().toString().take(10) && LocalDateTime.now().hour >= 9) {
        menueIsInThePast.value = true
    } else {
        menueIsInThePast.value = false
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

fun postBestellung() {
    val url = "http://10.0.2.2:8080/menue/bestellung"

    var formBody : RequestBody = FormBody.Builder()
        .add("amount", bestellungCount.value.toString())
        .add("comment", bestellungComment.value)
        .add("menueId", bestellungMenueId.value.toString())
        .add("orderedBy", bestellungErsteller.value)
        .add("orderedFor", bestellungFuer.value)
        .add("personalNumber", currUserPersonalNumber.value.toString())
        .add("timeId", bestellungZeitId.value.toString())
        .build()


    val request = Request.Builder()
        .url(url)
        .post(formBody)
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
                println("-------------------BESTELLT--------------------------")
                println("-----------------------------------------------------------------------")


                val gson = GsonBuilder().create()

                val collectionType: Type =
                    object : TypeToken<Collection<Bestellung?>?>() {}.type
                bestellungen = gson.fromJson(response.body!!.string(), collectionType)
            }
        }
    })

}


fun deleteBestellung(menuId : Int) {
    val url = "http://10.0.2.2:8080/menue/bestellung?id=$menuId"



    val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), Gson().toJson(""))


    val request = Request.Builder()
        .url(url)
        .put(body)
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
                println("-------------------BESTELLUNG GELÖSCHT--------------------------")
                println("-----------------------------------------------------------------------")


                InitBestellungen(currUser.value)

            }
        }
    })
}




var accessToken = mutableStateOf("")


fun checkAccessToken(): Boolean {

    val url = "http://10.0.2.2:8082/auth/realms/menuRealm/protocol/openid-connect/token"

    var body2 = FormBody.Builder()
        .add("client_id",   "menu-app")
        .add("grant_type", "password")
        .add("client_secret", "501a7f23-3154-4559-bce2-0dd996767574")
        .add("scope", "openid")
        .add("password", currUserPassword.value)
        .add("username", currUser.value)
        .build()


    val request = Request.Builder()
        .url(url)
        .post(body2)
        .build()

    val countDownLatch = CountDownLatch(1)

  client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (response.isSuccessful) {


                    println("-----------------------------------------------------------------------")
                    println("-----------------------------------------------------------------------")
                    println("-------------------LOGGED INNNNNNNNNNNNNNNNNN--------------------------")
                    println("-----------------------------------------------------------------------")
                    accessToken.value = response.body!!.string()
                }
                else{
                    throw IOException("Unexpected code $response")
                }
            }
            countDownLatch.countDown()
        }
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            countDownLatch.countDown()

        }
    })
    countDownLatch.await()
    return accessToken.value != ""

   //return client.newCall(request).execute().body.toString() != ""
}