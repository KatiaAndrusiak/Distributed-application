package com.example.repository

import com.example.entity.SubscriberInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait SubscriberInfoRepository extends JpaRepository[SubscriberInfo, Int] {

}