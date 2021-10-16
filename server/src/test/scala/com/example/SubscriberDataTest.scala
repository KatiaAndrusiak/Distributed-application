package com.example


import com.example.repository.SubscriberDataRepository
import com.example.entity.SubscriberData
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class SubscriberDataTest {

    @Autowired
    val em: TestEntityManager = null

    @Autowired
    val subscriberDataRepository: SubscriberDataRepository = null

    @Test
    def shouldReturnSubscriberData(): Unit = {
        // given
        val id: Int = 1

        // when
        val subscriber: SubscriberData = subscriberDataRepository.findById(id).orElseThrow(() => new NullPointerException)
        // then
        Assert.assertEquals(1, subscriber.getId)
        Assert.assertEquals("tester@gmail.com", subscriber.getEmail)
        Assert.assertEquals("tester123", subscriber.getPassword)
    }

    @Test(expected = classOf[NullPointerException])
    def shouldThrowExceptionWhenSubscriberNotExist(): Unit = {
        // given
        val id: Int = 2

        //when
        val subscriber: SubscriberData = subscriberDataRepository.findById(id).orElseThrow(() => new NullPointerException)

        //then
    }
}