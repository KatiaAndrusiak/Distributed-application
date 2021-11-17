package com.example.repository

import com.example.entity.SubscriberData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait SubscriberDataRepository extends JpaRepository[SubscriberData, Int] {
    def findByEmail(email: String): Option[SubscriberData]
    def existsByEmail(email: String): Boolean
}
