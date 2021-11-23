package com.example.security.jwt

import com.example.security.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

import java.io.IOException
import javax.servlet.{FilterChain, ServletException}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private val jwtUtils: JwtUtils  = null


    @Autowired
    private val userDetailsService: UserDetailsServiceImpl  = null

    @throws[ServletException]
    @throws[IOException]
    override protected def doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain): Unit = {
        try {
            val jwt: String = parseJwt(request)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                val username: String = jwtUtils.getUserNameFromJwtToken(jwt)

                val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
                val authentication: UsernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))

                SecurityContextHolder.getContext().setAuthentication(authentication)
            }
        } catch {
            case e: Exception => println("Cannot set user authentication: {}", e)
        }

        filterChain.doFilter(request, response)
    }

    private def parseJwt(request: HttpServletRequest): String  = {
        val headerAuth: String = request.getHeader("Authorization")

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else {
            null
        }
    }
}
