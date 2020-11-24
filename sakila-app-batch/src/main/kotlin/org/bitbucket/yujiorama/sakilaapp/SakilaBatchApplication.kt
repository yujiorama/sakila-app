package org.bitbucket.yujiorama.sakilaapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SakilaBatchApplication

fun main(args: Array<String>) {
    runApplication<SakilaBatchApplication>(*args)
}