package com.craftandtechnology.dockerlocalhost

import com.craftandtechnology.dockerlocalhost.config.AppConfig
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
	runApplication<AppConfig>(*args)
}
