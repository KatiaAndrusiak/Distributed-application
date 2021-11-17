package com.example.security.services

import com.example.entity.SubscriberData
import com.example.repository.SubscriberDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.{UserDetails, UserDetailsService, UsernameNotFoundException}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl extends UserDetailsService {
    @Autowired
    val subscriberData: SubscriberDataRepository = null

    @Transactional
    @throws[UsernameNotFoundException]
    override def loadUserByUsername(email: String): UserDetails = {
        val user: SubscriberData = subscriberData.findByEmail(email).getOrElse(throw new UsernameNotFoundException("User Not Found with email: " + email))

        UserDetailsImpl.build(user)
    }

}
