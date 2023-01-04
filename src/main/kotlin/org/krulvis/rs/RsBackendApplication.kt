package org.krulvis.rs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RsBackendApplication

fun main(args: Array<String>) {
    runApplication<RsBackendApplication>(*args)
}
