package com.shaber.movieticketkotlin.service

import com.shaber.movieticketkotlin.dto.UserDto
import com.shaber.movieticketkotlin.resp.RV
import com.shaber.movieticketkotlin.vo.UserVO

interface UserService {
    // 用户登录
    fun userLogin(userVO: UserVO): RV<UserDto>
    fun userRegister(userVO: UserVO): RV<UserDto>
}
