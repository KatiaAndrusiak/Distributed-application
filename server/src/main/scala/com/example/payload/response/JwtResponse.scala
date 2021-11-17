package com.example.payload.response

import java.util


class JwtResponse(var token: String, var id: Long, var firstName: String, var lastName: String, var email: String, var roles: util.List[String]) {
    private var `type` = "Bearer"

    def getFirstName: String = firstName

    def setFirstNamel(firstName: String): Unit = {
        this.firstName = firstName
    }

    def getLastName: String = lastName

    def setLastName(lastName: String): Unit = {
        this.lastName = lastName
    }

    def getAccessToken: String = token

    def setAccessToken(accessToken: String): Unit = {
        this.token = accessToken
    }

    def getTokenType: String = `type`

    def setTokenType(tokenType: String): Unit = {
        this.`type` = tokenType
    }

    def getId: Long = id

    def setId(id: Long): Unit = {
        this.id = id
    }

    def getEmail: String = email

    def setEmail(email: String): Unit = {
        this.email = email
    }

    def getRoles: util.List[String] = roles
}