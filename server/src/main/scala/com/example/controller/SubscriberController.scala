package com.example.controller

import com.example.model.NewSubscriber
import scalaj.http.{Http, HttpResponse}
import org.springframework.web.bind.annotation.{CrossOrigin, PostMapping, RequestBody, RestController}
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


@RestController
@CrossOrigin
class SubscriberController {

    @PostMapping(path = Array("/subscribers"))
    def createSubscriber(@RequestBody newSubscriber: NewSubscriber): Unit = {
        val response: HttpResponse[String] = Http("https://maps.googleapis.com/maps/api/geocode/json")
            .params(Seq("address" -> "Polska,Krakow,Reymonta,17", "key" -> "AIzaSyD4bXEAx4F33a2NXFK7akrYWbOExMP_X5k"))
            .asString
        println(response)
        println(newSubscriber)
        throw new ResponseStatusException(HttpStatus.OK, "Cause description here")
    }

}
