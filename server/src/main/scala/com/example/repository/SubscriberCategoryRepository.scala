package com.example.repository

import com.example.entity.{Subscriber, SubscriberCategory}
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait SubscriberCategoryRepository extends JpaRepository[SubscriberCategory, Int] {
    def findAllBySubscriber(subscriber: Subscriber): java.util.List[SubscriberCategory]
}