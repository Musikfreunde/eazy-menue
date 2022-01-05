package com.example.menuebestellung

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Menue(
    val appetizer: String,
    val code: String,
    val date: String,
    val dessert: String,
    val id: Int,
    val mainDish: String
)
