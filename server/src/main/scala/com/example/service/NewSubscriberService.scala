package com.example.service

import java.util
import com.example.entity.{Category, Role, Subscriber, SubscriberCategory, SubscriberData, SubscriberInfo}
import com.example.exception.{EmailAlreadyExistException, NoSuchAddressException, NoSuchStreetException}
import com.example.repository.{CategoryRepository, RoleRepository, SubscriberCategoryRepository, SubscriberDataRepository, SubscriberRepository}
import com.example.model.{ERole, NewSubscriber}
import org.json4s.{DefaultFormats, JValue}
import org.json4s.native.JsonMethods.parse
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import scalaj.http.Http

@Service
class NewSubscriberService(@Autowired subscriberRepository: SubscriberRepository,
                          @Autowired subscriberDataRepository: SubscriberDataRepository,
                          @Autowired categoryRepository: CategoryRepository,
                          @Autowired subscriberCategoryRepository: SubscriberCategoryRepository,
                          @Autowired encoder: PasswordEncoder,
                          @Autowired roleRepository: RoleRepository) {

    @Value("${jabeda.google.api.link}")
    private val apiLink: String = null

    @Value("${jabeda.google.api.key}")
    private val apiKEy: String = null

    def getSubscriberLocation(newSubscriber: NewSubscriber): Map[String,Double] = {
        implicit val formats = DefaultFormats
        val street = splitAddress(newSubscriber)._1.trim
        val buildingNumber = splitAddress(newSubscriber)._2.trim
        val fullAddress = newSubscriber.getCountry + "," + newSubscriber.getCity + "," + street + "," + buildingNumber

        val jsonObject = getLocationDetails(fullAddress)

        checkAddressExist(jsonObject)

        val lat = (jsonObject \ "results" \ "geometry" \ "location" \ "lat")(0).extract[Double]
        val lng = (jsonObject \ "results" \ "geometry" \ "location" \ "lng")(0).extract[Double]
//        println(lat)
//        println(lng)
        println(newSubscriber)
        Map("latitude" -> lat, "longitude" -> lng)
    }

    def registerSubscriber(newSubscriber: NewSubscriber, location: Map[String,Double]): Unit = {
        if (checkIfEmailExistInDataBase(newSubscriber.getEmail)) {
            throw EmailAlreadyExistException("Użytkownik z takim mailem już istnieje")
        }

        val street = splitAddress(newSubscriber)._1.trim
        val buildingNumber = splitAddress(newSubscriber)._2.trim

        val subscriber: Subscriber = new Subscriber
        val subscriberData: SubscriberData = new SubscriberData
        val subscriberInfo: SubscriberInfo = new SubscriberInfo

        subscriberData.setPassword(encoder.encode(newSubscriber.getPassword))
        subscriberData.setEmail(newSubscriber.getEmail)
        subscriberData.setSubscriber(subscriber)

        subscriberInfo.setPhone(newSubscriber.getPhone)
        subscriberInfo.setCountry(newSubscriber.getCountry)
        subscriberInfo.setCity(newSubscriber.getCity)
        subscriberInfo.setStreet(street)
        subscriberInfo.setBuildingNumber(buildingNumber)
        subscriberInfo.setLatitude(location("latitude"))
        subscriberInfo.setLongitude(location("longitude"))
        subscriberInfo.setSubscriber(subscriber)

        subscriber.setFname(newSubscriber.getFirstName)
        subscriber.setLname(newSubscriber.getLastName)
        subscriber.setSubscriberData(subscriberData)
        subscriber.setSubscriberInfo(subscriberInfo)

        subscriberRepository.save(subscriber)

        val strRoles: util.Set[String] = newSubscriber.getRole
        val roles: util.Set[Role] = new util.HashSet[Role]()

        if (strRoles == null) {
            val userRole: Role = roleRepository.findByName(ERole.ROLE_USER).getOrElse(throw new RuntimeException("Error: Role is not found."))
            roles.add(userRole)
        } else {
            strRoles.forEach {
                case "admin" =>
                    val adminRole: Role = roleRepository.findByName(ERole.ROLE_ADMIN).getOrElse(throw new RuntimeException("Error: Role is not found."))
                    roles.add(adminRole)
                case _ =>
                    val userRole: Role = roleRepository.findByName(ERole.ROLE_USER).getOrElse(throw new RuntimeException("Error: Role is not found."))
                    roles.add(userRole)

            }
        }
        subscriberData.setRoles(roles)
        subscriberRepository.save(subscriber)
        subscriberDataRepository.save(subscriberData)
    }

    def saveSubscriberCategory(newSubscriber: NewSubscriber): Unit = {
        val subscriberId: Int = subscriberDataRepository.findByEmail(newSubscriber.getEmail).get.getId
        val subscriber: Subscriber = subscriberRepository.findById(subscriberId).get()


        newSubscriber.getCategory.forEach(category => {
            val subscriberCategory = new SubscriberCategory
            val categoryToSet: Category = categoryRepository.findByName(category)
            subscriberCategory.setCategory(categoryToSet)
            subscriberCategory.setSubscriber(subscriber)
            subscriberCategoryRepository.save(subscriberCategory)
        })
    }

    def getLocationDetails(fullAddress: String): JValue = {
        val response = Http(apiLink)
            .params(Seq("address" -> fullAddress, "key" -> apiKEy))
            .asString

        // println(response.body)

        parse(response.body)
    }

    def checkIfEmailExistInDataBase(email: String): Boolean = {
        subscriberDataRepository.existsByEmail(email)
    }

    def checkAddressExist(jsonObject: JValue): Unit = {
        implicit val formats = DefaultFormats
        val results = (jsonObject \ "results")
        if (results.children.isEmpty) {
            throw  NoSuchAddressException("Nieprawidłowe dane: miasto oraz adres, proszę sprawdzić dane i spróbować jeszcze raz!")
        }
        val formattedAddress = (jsonObject \ "results" \ "formatted_address")(0).extract[String]
//        println(results.children.length)
//        println(formattedAddress)
        if (formattedAddress.split(",").length < 3) {
            throw  NoSuchStreetException("Nieprawidłowy adres, proszę sprawdzić dane i spróbować jeszcze raz!")
        }

    }

    def splitAddress(newSubscriber: NewSubscriber) : (String, String) = {
        val splitAddress = newSubscriber.getAddress.split(",")
        val street = splitAddress(0).trim
        val buildingNumber = splitAddress(1).trim
        (street, buildingNumber)
    }

}
