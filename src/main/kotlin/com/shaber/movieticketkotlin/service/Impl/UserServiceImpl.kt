package com.shaber.movieticketkotlin.service.Impl

import com.shaber.movieticketkotlin.dto.UserDto
import com.shaber.movieticketkotlin.mapper.UserMapper
import com.shaber.movieticketkotlin.pojo.User
import com.shaber.movieticketkotlin.resp.RV
import com.shaber.movieticketkotlin.service.UserService
import com.shaber.movieticketkotlin.utils.MD5WithSalt
import com.shaber.movieticketkotlin.utils.SnowflakeIdGenerator
import com.shaber.movieticketkotlin.vo.UserVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class UserServiceImpl:UserService {
    @Autowired
    lateinit var userMapper: UserMapper

    private final val snowflakeIdGenerator = SnowflakeIdGenerator(1, 1)

    override fun userLogin(userVO: UserVO): RV<UserDto> {
        // 根据传入的用户名或者手机号进行登录
        // 如果是手机号
        if (userVO.accountPhone != null) {
            // 手机号登录
            val user = userMapper.getUserByPhone(userVO.accountPhone) ?: return RV.error(400, "手机号不存在", null)
            if (!MD5WithSalt.verifyPassword(userVO.accountPwd, user.upwd)) {
                return RV.error(400, "密码错误", null)
            }
            // 登录成功，返回用户信息
            return RV.success("登录成功！", user.userToUserDto())
        } else if (userVO.accountUsername != null) {
            // 用户名登录
            val user = userMapper.getUserByUsername(userVO.accountUsername) ?: return RV.error(400, "用户名不存在", null)
            println(user)
            if (!MD5WithSalt.verifyPassword(userVO.accountPwd, user.upwd)) {
                return RV.error(400, "密码错误", null)
            }
            return RV.success("登录成功！", user.userToUserDto())
        }

        return RV.error(400, "用户名或手机号不能为空", null)
    }

    override fun userRegister(userVO: UserVO): RV<UserDto> {
        // 进行用户注册，首先需要判断手机号和用户名是否重复
        if (userMapper.getUserByPhone(userVO.accountPhone!!) != null) {
            return RV.error(400, "手机号重复！", null)
        }
        if (userMapper.getUserByUsername(userVO.accountUsername!!) != null) {
            return RV.error(400, "用户名重复！", null)
        }

        // 如果不出现重复才能进行注册
        // 新建一个User
        val user = User(snowflakeIdGenerator.nextId(),
            userVO.accountUsername,
            MD5WithSalt.encryptPasswordWithSalt(userVO.accountPwd),
            BigDecimal(0),
            userVO.accountPhone
        )
        // 注册user进入数据库
        if (userMapper.registerUser(user) <= 0) {
            return RV.error(400, "注册失败！", null)
        }

        // 注册成功，返回用户信息
        return RV.success("注册成功！",null)
    }

}
