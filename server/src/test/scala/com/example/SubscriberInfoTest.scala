package com.example

import com.example.entity.{SubscriberData, SubscriberInfo}
import com.example.repository.SubscriberInfoRepository
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class SubscriberInfoTest {

    @Autowired
    val em: TestEntityManager = null

    @Autowired
    val subscriberInfoRepository: SubscriberInfoRepository = null

    @Test
    def shouldReturnSubscriberInfo(): Unit = {
        // given
        val id: Int = 1

        // when
        val subscriberInfo: SubscriberInfo = subscriberInfoRepository.findById(id).orElseThrow(() => new NullPointerException)

        println(subscriberInfo)
        // then
        Assert.assertEquals(1, subscriberInfo.getId)
        Assert.assertEquals("2021-10-13", subscriberInfo.getDob.toString)
        Assert.assertEquals("+48574450911", subscriberInfo.getPhone)
        Assert.assertEquals("Polska", subscriberInfo.getCountry)
        Assert.assertEquals("Kraków", subscriberInfo.getCity)
        Assert.assertEquals("Dworska", subscriberInfo.getStreet)
        Assert.assertEquals("1A", subscriberInfo.getBuildingNumber)
        Assert.assertEquals(50.046006, subscriberInfo.getLatitude, 0.1)
        Assert.assertEquals(19.930139, subscriberInfo.getLongitude, 0.1)
    }

    @Test(expected = classOf[NullPointerException])
    def shouldThrowExceptionWhenSubscriberInfoNotExist(): Unit = {
        // given
        val id: Int = 2

        //when
        val subscriberInfo: SubscriberInfo = subscriberInfoRepository.findById(id).orElseThrow(() => new NullPointerException)

        //then
    }
}
