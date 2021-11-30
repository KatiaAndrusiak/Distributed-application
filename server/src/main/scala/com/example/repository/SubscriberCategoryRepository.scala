package com.example.repository

import com.example.entity.{Subscriber, SubscriberCategory}
import org.springframework.data.jpa.repository.{JpaRepository, Modifying, Query}
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
trait SubscriberCategoryRepository extends JpaRepository[SubscriberCategory, Int] {
    def findAllBySubscriber(subscriber: Subscriber): java.util.List[SubscriberCategory]

    @Modifying
    @Transactional
    @Query("delete from SubscriberCategory sc where sc.subscriber = ?1")
    def deleteSubscriberCategoriesBySubscriber(subscriber: Subscriber): Unit
}