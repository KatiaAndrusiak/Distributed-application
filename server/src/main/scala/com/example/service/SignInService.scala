package com.example.service

import com.example.entity.Subscriber
import com.example.repository.SubscriberCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util


@Service
class SignInService(@Autowired subscriberCategoryRepository: SubscriberCategoryRepository) {

    def getUserCategory(subscriber: Subscriber): util.List[String] = {
        val userCategories = new util.ArrayList[String]
        subscriberCategoryRepository.findAllBySubscriber(subscriber).forEach(category => {
            userCategories.add(category.getCategory.getName)
        })
        println(userCategories)
        userCategories
    }


}
