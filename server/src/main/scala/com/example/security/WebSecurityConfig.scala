package com.example.security

import com.example.security.jwt.{AuthEntryPointJwt, AuthTokenFilter}
import com.example.security.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    override val userDetailsService: UserDetailsServiceImpl = null

    @Autowired
    private val unauthorizedHandler: AuthEntryPointJwt = null

    @Bean
    def authenticationJwtTokenFilter() : AuthTokenFilter = new AuthTokenFilter()

    override def configure(auth: AuthenticationManagerBuilder): Unit = auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())

    @Bean
    @throws[Exception]
    override def authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean

    @Bean
    def passwordEncoder(): PasswordEncoder = new BCryptPasswordEncoder()

    @throws[Exception]
    override protected def configure(http: HttpSecurity): Unit = {
        // usunac /problems po skonczeniu implementacji logowania
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/auth/**").permitAll()
            .antMatchers("/categories").permitAll()
            .antMatchers("/problems").permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(authenticationJwtTokenFilter(), classOf[UsernamePasswordAuthenticationFilter])
    }
}
