package com.example.repository

import com.example.entity.SubscriberCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait SubscriberCategoryRepository extends JpaRepository[SubscriberCategory, Int] {

}