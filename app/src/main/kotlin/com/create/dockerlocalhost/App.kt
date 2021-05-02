package com.create.dockerlocalhost

import com.create.dockerlocalhost.config.AppConfig
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
	runApplication<AppConfig>(*args)
}
