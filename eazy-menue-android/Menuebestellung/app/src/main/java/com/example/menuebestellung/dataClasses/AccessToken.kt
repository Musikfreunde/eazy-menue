package com.example.menuebestellung.dataClasses

data class AccessToken(val access_token: String,
                       val expires_in: Int,
                       val refresh_expires_in: Int,
                       val refresh_token: String,
                       val token_type: String,
                       val id_token: String,
                       val session_state: String,
                       val scope: String
                       )
