package com.example.menuebestellung.dataClasses

data class BestellungDTO(
    var orderedBy: String,
    var amount: Int,
    var comment: String,
    var menueId: Int,
    var orderedFor: String,
    var timeId: Int,
    var personalNummer: Int
)
