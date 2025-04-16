package com.shaber.movieticketkotlin.controller

import com.shaber.movieticketkotlin.dto.UserDto
import com.shaber.movieticketkotlin.resp.RV
import com.shaber.movieticketkotlin.service.UserService
import com.shaber.movieticketkotlin.vo.UserVO
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户相关接口")
class UserController(@Autowired private val userService: UserService) {

    @PostMapping("/login")
    fun userLogin(@RequestBody userVO: UserVO):RV<UserDto> =
        userService.userLogin(userVO)

    @PostMapping("/register")
    fun userRegister(@RequestBody userVO: UserVO):RV<UserDto> =
        userService.userRegister(userVO)
}
