package com.example.security.services

import com.example.entity.{Subscriber, SubscriberData}
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import java.util
import scala.beans.BeanProperty

@SerialVersionUID(1L)
object UserDetailsImpl {
    def build(user: SubscriberData): UserDetailsImpl = {
        val authorities: util.List[GrantedAuthority] = new util.ArrayList[GrantedAuthority]()
        val it = user.getRoles.iterator
        while ( {it.hasNext}) {
            authorities.add(new SimpleGrantedAuthority(it.next.getName.name))
        }
        val subscriber = user.getSubscriber
        val subscriberInfo = subscriber.getSubscriberInfo
        new UserDetailsImpl(
            user.getId,
            subscriberInfo.getPhone,
            subscriberInfo.getCountry,
            subscriberInfo.getCity,
            subscriberInfo.getStreet,
            subscriberInfo.getBuildingNumber,
            subscriberInfo.getLatitude,
            subscriberInfo.getLongitude,
            subscriber.getFname,
            subscriber.getLname,
            user.getEmail,
            user.getPassword,
            subscriber,
            authorities
        )
    }
}

@SerialVersionUID(1L)
class UserDetailsImpl(
                         @BeanProperty var id: Int,
                         @BeanProperty var phone: String,
                         @BeanProperty var country: String,
                         @BeanProperty var city: String,
                         @BeanProperty var street: String,
                         @BeanProperty var buildingNumber: String,
                         @BeanProperty var latitude: Double,
                         @BeanProperty var longitude: Double,
                         @BeanProperty var firstName: String,
                         @BeanProperty var lastName: String,
                         @BeanProperty var email: String,
                         @JsonIgnore var password: String,
                         @BeanProperty var subscriber: Subscriber,
                         var authorities: util.Collection[_ <: GrantedAuthority]
                     ) extends UserDetails {
    override def getAuthorities: util.Collection[_ <: GrantedAuthority] = authorities

    override def getPassword: String = password

    override def getUsername: String = email

    override def isAccountNonExpired = true

    override def isAccountNonLocked = true

    override def isCredentialsNonExpired = true

    override def isEnabled = true

}