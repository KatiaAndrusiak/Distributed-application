package com.example.service

import com.example.entity.Subscriber
import com.example.model.NewSubscriber
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.springframework.stereotype.Service
import scalaj.http.Http

@Service
class NewSubscriberService {

    def getSubscriberLocation(newSubscriber: NewSubscriber): Map[String,Double] = {
        implicit val formats = DefaultFormats
        val splittedAddress = newSubscriber.getAddress.split(",")
        val street = splittedAddress(0).trim
        val buildingNumber = splittedAddress(1).trim

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

    def registerSubscriber(newSubscriber: NewSubscriber): Unit = {
        val subscriber: Subscriber = new Subscriber
    }

}
