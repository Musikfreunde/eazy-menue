package com.example.menuebestellung.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.menuebestellung.dataClasses.Oeffnungszeiten

@Composable
fun OeffnungszeitenItem(oeffnungszeiten: Oeffnungszeiten) {


    val checkedState = remember { mutableStateOf(oeffnungszeiten.chosen) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
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