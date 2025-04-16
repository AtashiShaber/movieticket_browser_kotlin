package com.shaber.movieticketkotlin.mapper

import com.shaber.movieticketkotlin.pojo.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserMapper {
    fun getUserByPhone(@Param("uphone") accountUser: String): User

    fun getUserByUsername(@Param("uname") accountUser: String): User
}
