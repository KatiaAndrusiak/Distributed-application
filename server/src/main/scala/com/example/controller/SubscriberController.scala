package com.example.controller

import com.example.model.NewSubscriber
import com.example.service.NewSubscriberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{CrossOrigin, PostMapping, RequestBody, RestController}
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


@RestController
@CrossOrigin
class SubscriberController(@Autowired val newSubscriberService: NewSubscriberService) {

    @PostMapping(path = Array("/subscribers"))
    def createSubscriber(@RequestBody newSubscriber: NewSubscriber): Unit = {
        val location = newSubscriberService.getSubscriberLocation(newSubscriber)
        newSubscriberService.registerSubscriber(newSubscriber, location)
        throw new ResponseStatusException(HttpStatus.OK, "Cause description here")
    }

}
