package com.shaber.movieticketkotlin.dto

import java.math.BigDecimal

data class UserDto(
    val uid:String,
    val uname:String,
    val uphone:String,
    val umoney:BigDecimal
)
