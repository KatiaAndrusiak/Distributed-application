package com.example

import com.example.entity.Subscriber
import com.example.exception.{EmailAlreadyExistException, NoSuchAddressException, NoSuchStreetException}
import com.example.model.NewSubscriber
import com.example.repository.SubscriberRepository
import com.example.service.NewSubscriberService
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@RunWith(classOf[SpringRunner])
@ActiveProfiles(Array("test"))
class NewSubscriberServiceTest {

    @Autowired
    val newSubscriberService: NewSubscriberService = null

    @Autowired
    val subscriberRepository: SubscriberRepository = null


    @Test
    def shouldReturnSubscriberLocation: Unit = {
        // given
        val newSubscriber: NewSubscriber = NewSubscriber("test", "test", "+48555555555", "test@email.com", "123456", "Polska", "Kraków", "Pawia, 23", null, null)

        //when
        val location = newSubscriberService.getSubscriberLocation(newSubscriber)

        //then
        Assert.assertEquals(50.073012, location("latitude"), 0.00001)
        Assert.assertEquals(19.945507, location("longitude"), 0.00001)
    }

    @Test(expected = classOf[EmailAlreadyExistException])
    def shouldThrownEmailAlreadyExistException: Unit = {
        // given
        val newSubscriber: NewSubscriber = NewSubscriber("test", "test", "+48555555555", "vsamkovich@ukr.net", "123456", "Polska", "Kraków", "Pawia, 23", null, null)
        val location = Map("latitude" -> 50.073012, "longitude" -> 19.945507)

        //when
        newSubscriberService.registerSubscriber(newSubscriber, location)

        //then

    }

    @Test(expected = classOf[NoSuchAddressException])
    def shouldThrownNoSuchAddressException: Unit = {
        // given
        val fullAddress = "Polska,Semkovych,Semkovych,17"
        val locationDetails = newSubscriberService.getLocationDetails(fullAddress)

        //when
        newSubscriberService.checkAddressExist(locationDetails)

        //then
    }

    @Test(expected = classOf[NoSuchStreetException])
    def shouldThrownNoSuchStreetException: Unit = {
        // given
        val fullAddress = "Polska,Kraków,Semkovych,17"
        val locationDetails = newSubscriberService.getLocationDetails(fullAddress)

        //when
        newSubscriberService.checkAddressExist(locationDetails)

        //then

    }

    @Test
    def shouldCheckMailExisting: Unit = {
        // given
        val email = "tester@gmail.com"

        //when
        val isMailExist = newSubscriberService.checkIfEmailExistInDataBase(email)

        //then
        Assert.assertEquals(true, isMailExist)
    }

    @Test
    def shouldReturnUserCategories: Unit = {
        // given
        val subscriberId: Int = 6
        val subscriber: Subscriber = subscriberRepository.findById(subscriberId).orElseThrow(() => new NullPointerException)

        //when
        val subscriberCategories = newSubscriberService.getUserCategory(subscriber)

        //then
        Assert.assertEquals("Woda", subscriberCategories.get(0))
        Assert.assertEquals("Zniszczenia", subscriberCategories.get(1))
    }

}
