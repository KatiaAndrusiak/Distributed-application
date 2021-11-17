package com.example.security.jwt

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

@Component
class AuthEntryPointJwt extends AuthenticationEntryPoint {

    @throws[IOException]
    @throws[ServletException]
    override def commence(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, e: AuthenticationException): Unit = {
        println("Unauthorized error: {}", e.getMessage())
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
    }
}
