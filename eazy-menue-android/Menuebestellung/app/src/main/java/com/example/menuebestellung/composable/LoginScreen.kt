package com.example.menuebestellung.composable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.menuebestellung.*

var isLoggedIn = mutableStateOf(false)
var currUser = mutableStateOf("");
var currUserPassword = mutableStateOf("");
var currUserPersonalNumber = mutableStateOf(1023) //Temporär als user spabo

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavHostController) {


    val (focusRequester) = FocusRequester.createRefs()
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    if (!isLoggedIn.value) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 30.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(
                        unbounded = false,
                        align = Alignment.Center
                    )
                    .padding(12.dp)
            ) {

                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        currUser.value = name
                    },
                    label = { Text(text = "Username") }
                )
            }
            Row() {
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        currUserPassword.value = password
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text(text = "Password") }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(
                        unbounded = false,
                        align = Alignment.Center
                    )
                    .padding(12.dp)
            ) {


                Button(onClick = {
                    if (!name.isEmpty() && !password.isEmpty() && checkAccessToken()) {

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
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentSize(
                        unbounded = false,
                        align = Alignment.Center
                    )
                    .padding(12.dp)
            ) {
                Button(onClick = {
                    currUser.value = ""
                    currUserPassword.value = ""
                    menuesFilteredByDate = menuesFilteredByDate - menuesFilteredByDate
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

    }
    getOeffnungszeiten()
}
