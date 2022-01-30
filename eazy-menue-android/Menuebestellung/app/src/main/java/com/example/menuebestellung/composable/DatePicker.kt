package com.example.menuebestellung

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.menuebestellung.composable.isLoggedIn
import com.example.menuebestellung.composable.uebersichtDate
import java.util.*

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
            var temp = month.inc()
            if (dayOfMonth >= 1 && dayOfMonth <= 9) {

                if (temp.toString().length == 1) {
                    date.value = "$year-0${temp}-0$dayOfMonth"
                } else {
                    date.value = "$year-${temp}-0$dayOfMonth"
                }
            } else {
                if (temp.toString().length == 1) {
                    date.value = "$year-0${temp}-$dayOfMonth"
                } else {
                    date.value = "$year-${temp}-$dayOfMonth"
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
        Spacer(modifier = Modifier.padding(10.dp))
        Row() {
            Button(
                onClick = {
                    datePickerDialog.show()
                },
                enabled = isLoggedIn.value,
                modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()
            ) {
                Text(text = "Date: ${uebersichtDate.value}")
            }
        }


    }
}