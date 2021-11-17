package com.example.exception

import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.springframework.http.HttpStatus

object BuildExceptionHandler {
    def createErrorMessage(exception: Exception): String = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val messageMap: Map[String, Any] = Map[String, Any]("message" -> exception.getMessage, "status" -> HttpStatus.BAD_REQUEST.value)
        Serialization.write(messageMap)
    }
}
