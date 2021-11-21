package com.example

import com.example.exception.{EmailAlreadyExistException, NoSuchAddressException, NoSuchStreetException}
import com.example.model.NewSubscriber
import com.example.repository.{CategoryRepository, RoleRepository, SubscriberCategoryRepository, SubscriberDataRepository, SubscriberRepository}
import com.example.service.{NewSubscriberService, ProblemService}
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@RunWith(classOf[SpringRunner])
class NewSubscriberServiceTest {

    @Autowired
    val newSubscriberService: NewSubscriberService = null


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

}
