package com.shaber.movieticketkotlin.service.Impl

import com.shaber.movieticketkotlin.dto.UserDto
import com.shaber.movieticketkotlin.mapper.UserMapper
import com.shaber.movieticketkotlin.resp.RV
import com.shaber.movieticketkotlin.service.UserService
import com.shaber.movieticketkotlin.utils.MD5WithSalt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl:UserService {
    @Autowired
    lateinit var userMapper: UserMapper

    override fun userLogin(accountUser: String, accoutnPwd: String): RV<UserDto> {
        // 根据传入的用户名或者手机号进行登录
        // 如果是手机号
        if (accountUser.length == 11) {
            // 手机号登录
            val user = userMapper.getUserByPhone(accountUser) ?: return RV.error(400, "手机号不存在", null)
            if (!MD5WithSalt.verifyPassword(accoutnPwd, user.upwd)) {
                return RV.error(400, "密码错误", null)
            }
            // 登录成功，返回用户信息
            return RV.success("登录成功！", user.userToUserDto())
        } else {
            // 用户名登录
            val user = userMapper.getUserByUsername(accountUser) ?: return RV.error(400, "用户名不存在", null)
            println(user)
            if (!MD5WithSalt.verifyPassword(accoutnPwd, user.upwd)) {
                return RV.error(400, "密码错误", null)
            }
            return RV.success("登录成功！", user.userToUserDto())
        }
    }

}
