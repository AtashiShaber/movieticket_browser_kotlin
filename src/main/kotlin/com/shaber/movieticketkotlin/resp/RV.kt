package com.shaber.movieticketkotlin.resp

import com.fasterxml.jackson.annotation.JsonAutoDetect

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RV<T>(
    private var code: Int,
    private val success: Boolean,
    private val msg: String,
    private var data: T? = null
) {
    companion object {
        // 成功
        fun <T> success(msg: String, data: T?) = RV(200, true, msg, data)

        // 失败
        fun <T> error(code: Int, msg: String, data: T?) = RV(code, false, msg , data)
    }
}
