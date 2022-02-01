package com.example.menuebestellung.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
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
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Text(
            text = bestellung.menueName,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = bestellung.createdAt.take(10),
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = bestellung.menueDate,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )

    }

}