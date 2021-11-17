package com.example.security.jwt

import com.example.security.services.UserDetailsImpl
import io.jsonwebtoken.{ExpiredJwtException, Jwts, MalformedJwtException, SignatureAlgorithm, SignatureException, UnsupportedJwtException}
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

import java.util.Date

@Component
class JwtUtils {

    @Value("${jabeda.app.jwtSecret}")
    private var jwtSecret: String = _

    @Value("${jabeda.app.jwtExpirationMs}")
    private var jwtExpirationMs: Int = _

    def generateJwtToken (authentication: Authentication): String = {
        val userPrincipal = authentication.getPrincipal.asInstanceOf[UserDetailsImpl]

        Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    def getUserNameFromJwtToken(token: String): String = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody.getSubject

    def validateJwtToken(authToken: String): Boolean = {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch {
            case e: SignatureException => println("Invalid JWT signature: {}", e.getMessage)
            case e: MalformedJwtException  => println("Invalid JWT signature: {}", e.getMessage)
            case e: ExpiredJwtException  => println("Invalid JWT signature: {}", e.getMessage)
            case e: UnsupportedJwtException  => println("Invalid JWT signature: {}", e.getMessage)
            case e: IllegalArgumentException  => println("Invalid JWT signature: {}", e.getMessage)
        }
        false
    }
}
