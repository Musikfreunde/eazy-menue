package com.example.menuebestellung.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.menuebestellung.R
import com.example.menuebestellung.bestellungen
import com.example.menuebestellung.dataClasses.Bestellung
import com.example.menuebestellung.deleteBestellung
import com.example.menuebestellung.menues
import java.time.LocalDateTime
import java.util.*


var isInFuture = mutableStateOf(true)

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BestellungItem(bestellung: Bestellung) {

    val deletedItem = remember { mutableStateListOf<Bestellung>() }

    var mainDish = ""
    var mainDishDate = ""
    var mainDishId = 0
    menues.forEach { menue ->
        if (menue.date == bestellung.menueDate) {
            mainDish = menue.mainDish
            mainDishDate = bestellung.createdAt
            mainDishId = menue.id
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
        Row(
            horizontalArrangement = Arrangement.End


        ) {
                        AnimatedVisibility(
                            visible = !deletedItem.contains(bestellung),
                            enter = expandVertically(),
                            exit = shrinkVertically(
                                animationSpec = tween(
                                    durationMillis = 1000
                                )
                            )
                        ) {
                            Card(
                                modifier = Modifier
                                    // The space between each card and the other
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .wrapContentSize(Alignment.Center),
                                shape = MaterialTheme.shapes.small,
                                backgroundColor = MaterialTheme.colors.secondary
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(Modifier.padding(8.dp)) {
                                        Text(
                                            text = bestellung.menueName,
                                            style = MaterialTheme.typography.h2,
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            color = MaterialTheme.colors.onSurface,
                                        )

                                        Text(
                                            text = bestellung.menueDate,
                                            style = MaterialTheme.typography.body2,
                                            modifier = Modifier.padding(16.dp)
                                        )

                                    }
                                }

                                if (bestellung.menueDate > LocalDateTime.now().toString()
                                        .take(10)
                                ) {
                                    IconButton(onClick = {
                                        deleteBestellung(bestellung.id)
                                        deletedItem.add(bestellung)
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete"
                                        )
                                    }
                                }
                            }

                        }

        }
    }
}


