package com.example


import com.example.repository.SubscriberRepository
import com.example.entity.Subscriber
import org.junit.Test
import org.junit.Assert
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class SubscriberTest {

    @Autowired
    val em: TestEntityManager = null

    @Autowired
    val subscriberRepository: SubscriberRepository = null

    @Test
    def shouldReturnSubscriber(): Unit = {
        // given
        val id: Int = 2

        // when
        val subscriber: Subscriber = subscriberRepository.findById(id).orElseThrow(() => new NullPointerException)

        // then
        Assert.assertEquals(2, subscriber.getId)
        Assert.assertEquals("test", subscriber.getFname)
        Assert.assertEquals("test", subscriber.getLname)
    }

    @Test(expected = classOf[NullPointerException])
    def shouldThrowExceptionWhenSubscriberNotExist(): Unit = {
        // given
        val id: Int = 100000

        // when
        val subscriber: Subscriber = subscriberRepository.findById(id).orElseThrow(() => new NullPointerException)

        // then
    }
}