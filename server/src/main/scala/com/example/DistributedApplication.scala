package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BootConfig


object Application extends App {
    SpringApplication.run(classOf[BootConfig])
}