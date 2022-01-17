package com.example.menuebestellung

data class BestellungDTO(
    var orderedBy: String,
    var amount: Int,
    var comment: String,
    var menueId: Int,
    var orderedFor: String,
    var timeId: Int,
    var personalNumber: Int
)
