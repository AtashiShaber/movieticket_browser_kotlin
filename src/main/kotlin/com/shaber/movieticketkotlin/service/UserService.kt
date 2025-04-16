package com.shaber.movieticketkotlin.service

import com.shaber.movieticketkotlin.dto.UserDto
import com.shaber.movieticketkotlin.resp.RV

interface UserService {
    // 用户登录
    fun userLogin(accountUser: String, accoutnPwd: String): RV<UserDto>
}
