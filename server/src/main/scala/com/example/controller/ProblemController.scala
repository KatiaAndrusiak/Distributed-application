package com.example.controller

import org.springframework.web.bind.annotation.{GetMapping, RestController}


@RestController
class ProblemController {

    @GetMapping(path = Array("/"))
    def demo: String = "Hello Scala"

}
