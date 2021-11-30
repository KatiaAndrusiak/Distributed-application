package com.example.payload.response

import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.springframework.http.{HttpStatus, ResponseEntity}

object BuildOkResponse {
    def createOkResponse: ResponseEntity[AnyRef] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val messageMap: Map[String, Any] = Map[String, Any]("status" -> HttpStatus.OK.value)
        val message = Serialization.write(messageMap)
        new ResponseEntity[AnyRef](message, HttpStatus.OK)
    }
}
