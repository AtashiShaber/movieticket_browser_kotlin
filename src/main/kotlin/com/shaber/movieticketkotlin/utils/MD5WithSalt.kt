package com.shaber.movieticketkotlin.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64

object MD5WithSalt {

    // 使用MD5和盐值加密密码
    @Throws(NoSuchAlgorithmException::class)
    fun encryptPasswordWithSalt(password: String): String {
        // 生成盐值
        val salt = generateSalt()

        // 合并密码和盐值
        val saltedPassword = password + salt

        // 使用MD5加密
        val md = MessageDigest.getInstance("MD5")
        val hash = md.digest(saltedPassword.toByteArray())

        // 将加密后的字节数组转换成十六进制字符串
        val hashString = bytesToHex(hash)

        // 将盐值和加密后的密码结合在一起存储
        return "$hashString:$salt" // 密码和盐值用冒号分隔
    }

    // 验证密码是否与已加密密码匹配
    @Throws(NoSuchAlgorithmException::class)
    fun verifyPassword(inputPassword: String, storedPassword: String): Boolean {
        // 从存储的加密密码中分离出加密后的密码和盐值
        val parts = storedPassword.split(":")
        val encryptedPassword = parts[0]
        val salt = parts[1]

        // 将输入的密码与盐值结合后加密
        val saltedPassword = inputPassword + salt
        val md = MessageDigest.getInstance("MD5")
        val hash = md.digest(saltedPassword.toByteArray())

        // 将加密后的字节数组转换成十六进��字符串
        val hashString = bytesToHex(hash)

        // 比较加密后的密码是否与存储的密码一致
        return hashString == encryptedPassword
    }

    // 生成随机盐值
    private fun generateSalt(): String {
        val random = SecureRandom()
        val saltBytes = ByteArray(16) // 盐值的长度，通常是16字节
        random.nextBytes(saltBytes)
        return Base64.getEncoder().encodeToString(saltBytes) // 将盐值编码为Base64字符串
    }

    // 将字节数组转换为十六进制字符串
    private fun bytesToHex(bytes: ByteArray): String {
        val hexString = StringBuilder()
        for (b in bytes) {
            val hex = Integer.toHexString(0xff and b.toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun main(args: Array<String>) {
        val password = "123"

        // 加密密码
        val encryptedPassword = encryptPasswordWithSalt(password)
        println("加密密码: $encryptedPassword")

        // 验证密码
        val inputPassword = "123"
        val isPasswordCorrect = verifyPassword(inputPassword, encryptedPassword)
        println("验证密码: $isPasswordCorrect")
    }
}
