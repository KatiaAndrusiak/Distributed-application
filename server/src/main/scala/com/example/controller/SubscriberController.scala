package com.example.controller

import com.example.model.NewSubscriber
import com.example.service.NewSubscriberService
import org.json4s.NoTypeHints
import org.json4s.jackson.JsonMethods.{compact, render}
import org.json4s.jackson.Serialization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{CrossOrigin, ExceptionHandler, PostMapping, RequestBody, RestController}
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.server.ResponseStatusException

import javax.validation.{ConstraintViolationException, Valid}


@RestController
@CrossOrigin
class SubscriberController(@Autowired val newSubscriberService: NewSubscriberService) {

    @PostMapping(path = Array("/subscribers"))
    def createSubscriber(@Valid
                         @RequestBody newSubscriber: NewSubscriber): ResponseEntity[_] = {

        implicit val formats = Serialization.formats(NoTypeHints)
        val location = newSubscriberService.getSubscriberLocation(newSubscriber)
        newSubscriberService.registerSubscriber(newSubscriber, location)
        newSubscriberService.saveSubscriberCategory(newSubscriber)
        val messageMap: Map[String, String] = Map[String, String]("status" -> HttpStatus.OK.value.toString)
        val message = Serialization.write(messageMap)
        new ResponseEntity[AnyRef](message, HttpStatus.OK)

    }


    @ExceptionHandler(Array(classOf[ConstraintViolationException]))
    def handleSubscriberException(constraintViolationException : ConstraintViolationException): ResponseEntity[_] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val violations = constraintViolationException.getConstraintViolations
        var errorMap: Map[String, String] = Map[String, String]()
        if (!violations.isEmpty) {
            violations.forEach(violation => errorMap = errorMap + (violation.getPropertyPath.toString -> violation.getMessage)
            )
        }
        errorMap = errorMap + ("status" -> HttpStatus.NOT_FOUND.value().toString)
        val errorMessage = Serialization.write(errorMap)
        new ResponseEntity[AnyRef](errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Array(classOf[IllegalArgumentException]))
    def handleEmailException():ResponseEntity[_] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val messageMap: Map[String, String] = Map[String, String]("message" -> "Użytkownik z takim mailem już istnieje", "status" -> HttpStatus.BAD_REQUEST.value.toString)
        val message = Serialization.write(messageMap)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }

}
