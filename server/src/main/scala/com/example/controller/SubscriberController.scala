package com.example.controller

import com.example.exception.{EmailAlreadyExistException, NoSuchAddressException, NoSuchStreetException}
import com.example.model.NewSubscriber
import com.example.service.NewSubscriberService
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{CrossOrigin, ExceptionHandler, PostMapping, RequestBody, ResponseStatus, RestController}
import org.springframework.http.HttpStatus

import javax.validation.{ConstraintViolationException, Valid}


@RestController
@CrossOrigin
class SubscriberController(@Autowired val newSubscriberService: NewSubscriberService) {

    @PostMapping(path = Array("/subscribers"))
    def createSubscriber(@Valid
                         @RequestBody newSubscriber: NewSubscriber): ResponseEntity[_] = {

        implicit val formats = Serialization.formats(NoTypeHints)
        val location = newSubscriberService.getSubscriberLocation(newSubscriber)
        val messageMap: Map[String, String] = Map[String, String]("status" -> HttpStatus.OK.value.toString)
        val message = Serialization.write(messageMap)

        newSubscriberService.registerSubscriber(newSubscriber, location)
        newSubscriberService.saveSubscriberCategory(newSubscriber)

        new ResponseEntity[AnyRef](message, HttpStatus.OK)
    }


    @ExceptionHandler(Array(classOf[ConstraintViolationException]))
    def handleSubscriberException(constraintViolationException : ConstraintViolationException): ResponseEntity[_] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val violations = constraintViolationException.getConstraintViolations
        var errorMap: Map[String, Any] = Map[String, Any]()
        if (!violations.isEmpty) {
            violations.forEach(violation => errorMap = errorMap + (violation.getPropertyPath.toString -> violation.getMessage)
            )
        }
        errorMap = errorMap + ("status" -> HttpStatus.NOT_FOUND.value)
        val errorMessage = Serialization.write(errorMap)
        new ResponseEntity[AnyRef](errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Array(classOf[EmailAlreadyExistException]))
    def handleEmailException(exception: EmailAlreadyExistException):ResponseEntity[_] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val messageMap: Map[String, Any] = Map[String, Any]("message" -> exception.getMessage, "status" -> HttpStatus.BAD_REQUEST.value)
        val message = Serialization.write(messageMap)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Array(classOf[NoSuchAddressException]))
    def handleAddressException(exception: NoSuchAddressException):ResponseEntity[_] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val messageMap: Map[String, Any] = Map[String, Any]("message" -> exception.getMessage, "status" -> HttpStatus.BAD_REQUEST.value)
        val message = Serialization.write(messageMap)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Array(classOf[NoSuchStreetException]))
    def handleStreetException(exception: NoSuchStreetException):ResponseEntity[_] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val messageMap: Map[String, Any] = Map[String, Any]("message" -> exception.getMessage, "status" -> HttpStatus.BAD_REQUEST.value)
        val message = Serialization.write(messageMap)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }

}
