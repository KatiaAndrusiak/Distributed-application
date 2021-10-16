package com.example.repository

import com.example.entity.Subscriber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
trait SubscriberRepository extends JpaRepository[Subscriber, Int] {
    def findByFname(name: String): Subscriber
}
