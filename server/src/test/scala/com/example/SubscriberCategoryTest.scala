package com.example

import com.example.entity.SubscriberCategory
import com.example.repository.SubscriberCategoryRepository
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class SubscriberCategoryTest {

    @Autowired
    val em: TestEntityManager = null

    @Autowired
    val subscriberCategoryRepository: SubscriberCategoryRepository = null

    @Test
    def shouldReturnSubscriberCategory(): Unit = {
        // given
        val id: Int = 2

        //when
        val subscriberCategory: SubscriberCategory = subscriberCategoryRepository.findById(id).orElseThrow(() => new NullPointerException)

        //then
        Assert.assertEquals(2, subscriberCategory.getId)
        Assert.assertEquals(4, subscriberCategory.getCategory.getId)
        Assert.assertEquals(1, subscriberCategory.getSubscriber.getId)
    }

    @Test(expected = classOf[NullPointerException])
    def shouldThrowExceptionWhenSubscriberCategoryNotExist(): Unit = {
        // given
        val id: Int = 100000

        // when
        val subscriberCategory: SubscriberCategory = subscriberCategoryRepository.findById(id).orElseThrow(() => new NullPointerException)

        // then
    }

}
