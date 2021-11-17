package com.example.payload.response

class MessageResponse(var message: String) {
    def getMessage: String = message

    def setMessage(message: String): Unit = {
        this.message = message
    }
}