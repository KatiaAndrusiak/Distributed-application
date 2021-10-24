package com.example.controller

import org.springframework.web.bind.annotation.{CrossOrigin, PostMapping, RestController}
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


@RestController
@CrossOrigin
class SubscriberController {

    @PostMapping(path = Array("/subscribers"))
    def createSubscriber(): Unit = {
        throw new ResponseStatusException(HttpStatus.OK, "Cause description here")
    }

}
