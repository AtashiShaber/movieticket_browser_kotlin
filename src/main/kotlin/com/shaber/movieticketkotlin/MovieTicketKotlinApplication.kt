package com.shaber.movieticketkotlin

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "API测试",
        version = "1.0",
        description = "这是一个用于测试的 API 文档"
    )
)
@MapperScan("com.shaber.movieticketkotlin.mapper")
class MovieTicketKotlinApplication

fun main(args: Array<String>) {
    runApplication<MovieTicketKotlinApplication>(*args)
}
