package com.example.menuebestellung.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.menuebestellung.dataClasses.Bestellung
import com.example.menuebestellung.menues

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
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Black)

    ) {

        Text(
            text = bestellung.menueName,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)

        )
        Text(
            text = bestellung.createdAt.take(10),
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)
        )
        Text(
            text = bestellung.menueDate,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)

        )
        Column() {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }

    }

}