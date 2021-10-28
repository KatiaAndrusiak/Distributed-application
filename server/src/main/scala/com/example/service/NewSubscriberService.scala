package com.example.service

import com.example.entity.{Subscriber, SubscriberData, SubscriberInfo}
import com.example.repository.SubscriberRepository
import com.example.model.NewSubscriber
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import scalaj.http.Http

@Service
class NewSubscriberService(@Autowired subscriberRepository: SubscriberRepository) {

    def splitAddress(newSubscriber: NewSubscriber) : (String, String) = {
        val splittedAddress = newSubscriber.getAddress.split(",")
        val street = splittedAddress(0).trim
        val buildingNumber = splittedAddress(1).trim
        (street, buildingNumber)
    }

    def getSubscriberLocation(newSubscriber: NewSubscriber): Map[String,Double] = {
        implicit val formats = DefaultFormats
        val street = splitAddress(newSubscriber)._1.trim
        val buildingNumber = splitAddress(newSubscriber)._2.trim

        val fullAddress = newSubscriber.getCountry + "," + newSubscriber.getCity + "," + street + "," + buildingNumber

        val response = Http("https://maps.googleapis.com/maps/api/geocode/json")
            .params(Seq("address" -> fullAddress, "key" -> "AIzaSyD4bXEAx4F33a2NXFK7akrYWbOExMP_X5k"))
            .asString
        val jsonObject = parse(response.body)
        val lat = (jsonObject \ "results" \ "geometry" \ "location" \ "lat")(0).extract[Double]
        val lng = (jsonObject \ "results" \ "geometry" \ "location" \ "lng")(0).extract[Double]
        println(lat)
        println(lng)
        println(newSubscriber)
        Map("latitude" -> lat, "longitude" -> lng)
    }

    def registerSubscriber(newSubscriber: NewSubscriber, location: Map[String,Double]): Unit = {
        val street = splitAddress(newSubscriber)._1.trim
        val buildingNumber = splitAddress(newSubscriber)._2.trim

        val subscriber: Subscriber = new Subscriber
        val subscriberData: SubscriberData = new SubscriberData
        val subscriberInfo: SubscriberInfo = new SubscriberInfo

        subscriberData.setPassword(newSubscriber.getPassword)
        subscriberData.setEmail(newSubscriber.getEmail)
        subscriberData.setSubscriber(subscriber)

        subscriberInfo.setDob(newSubscriber.getDob)
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

        //subscriberRepository.save(subscriber)
    }

}
