package com.example

import com.example.entity.Subscriber
import com.example.repository.SubscriberRepository
import com.example.service.SignInService
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@RunWith(classOf[SpringRunner])
@ActiveProfiles(Array("test"))
class SignInServiceTest {

    @Autowired
    val signInService: SignInService = null

    @Autowired
    val subscriberRepository: SubscriberRepository = null

    @Test
    def shouldReturnUserCategories: Unit = {
        // given
        val subscriberId: Int = 6
        val subscriber: Subscriber = subscriberRepository.findById(subscriberId).orElseThrow(() => new NullPointerException)

        //when
        val subscriberCategories = signInService.getUserCategory(subscriber)

        //then
        Assert.assertEquals("Woda", subscriberCategories.get(0))
        Assert.assertEquals("Zniszczenia", subscriberCategories.get(1))
    }

}
