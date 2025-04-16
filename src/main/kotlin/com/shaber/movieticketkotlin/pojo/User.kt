package com.shaber.movieticketkotlin.pojo

import com.shaber.movieticketkotlin.dto.UserDto
import java.math.BigDecimal

data class User (
    var uid: String,
    var uname: String,
    var upwd:String,
    var umoney:BigDecimal = BigDecimal(0),
    var uphone:String
) {
    fun userToUserDto(): UserDto {
        return UserDto(
            uid = this.uid ?: "",
            uname = this.uname ?: "",
            uphone = this.uphone ?: "",
            umoney = this.umoney
        )
    }
}
