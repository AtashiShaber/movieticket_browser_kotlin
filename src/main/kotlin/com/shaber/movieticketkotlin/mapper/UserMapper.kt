package com.shaber.movieticketkotlin.mapper

import com.shaber.movieticketkotlin.pojo.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserMapper {
    fun getUserByPhone(@Param("uphone") accountUser: String): User?

    fun getUserByUsername(@Param("uname") accountUser: String): User?

    @Insert("INSERT INTO `user` (uid, uname, upwd, umoney,uphone) " +
            "VALUES (#{uid}, #{uname}, #{upwd}, #{umoney},#{uphone})")
    fun registerUser(user: User): Int
}
