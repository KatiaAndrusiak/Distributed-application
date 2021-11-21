package com.example.payload.response

import scala.beans.BeanProperty

class JwtResponse(
                     var token: String,
                     @BeanProperty var id: Long,
                     @BeanProperty var phone: String,
                     @BeanProperty var country: String,
                     @BeanProperty var city: String,
                     @BeanProperty var street: String,
                     @BeanProperty var buildingNumber: String,
                     @BeanProperty var firstName: String,
                     @BeanProperty var lastName: String,
                     @BeanProperty var email: String) {
    private var `type` = "Bearer"

    def getAccessToken: String = token

    def setAccessToken(accessToken: String): Unit = {
        this.token = accessToken
    }

    def getTokenType: String = `type`

    def setTokenType(tokenType: String): Unit = {
        this.`type` = tokenType
    }
}