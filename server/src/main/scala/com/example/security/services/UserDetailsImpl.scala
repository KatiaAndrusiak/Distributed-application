package com.example.security.services

import com.example.entity.SubscriberData
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import java.util

@SerialVersionUID(1L)
object UserDetailsImpl {
    def build(user: SubscriberData): UserDetailsImpl = {
        val authorities: util.List[GrantedAuthority] = new util.ArrayList[GrantedAuthority]()
        val it = user.getRoles.iterator
        while ( {it.hasNext}) {
            authorities.add(new SimpleGrantedAuthority(it.next.getName.name))
        }
        new UserDetailsImpl(user.getId, user.getSubscriber.getFname, user.getSubscriber.getLname, user.getEmail, user.getPassword, authorities)
    }
}

@SerialVersionUID(1L)
class UserDetailsImpl(var id: Int, var firstName: String, var lastName: String, var email: String, @JsonIgnore var password: String, var authorities: util.Collection[_ <: GrantedAuthority]) extends UserDetails {
    override def getAuthorities: util.Collection[_ <: GrantedAuthority] = authorities

    def getId: Long = id

    def getEmail: String = email

    def getFirstName: String = firstName

    def getLastName: String = lastName

    override def getPassword: String = password

    override def getUsername: String = email

    override def isAccountNonExpired = true

    override def isAccountNonLocked = true

    override def isCredentialsNonExpired = true

    override def isEnabled = true

}