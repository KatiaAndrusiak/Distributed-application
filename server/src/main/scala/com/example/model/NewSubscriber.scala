package com.example.model

import java.time.LocalDate
import scala.beans.BeanProperty


object NewSubscriber {
    def apply(name: String,
              lastName: String,
              dob: LocalDate,
              number: String,
              email: String,
              password: String,
              country: String,
              city: String,
              street: String,
              buildingNumber: String) : NewSubscriber = new NewSubscriber(name, lastName, dob, number, email, password, country,city, street, buildingNumber)
}

class NewSubscriber(@BeanProperty val firstName: String,
                    @BeanProperty val lastName: String,
                    @BeanProperty val dob: LocalDate,
                    @BeanProperty val phone: String,
                    @BeanProperty val email: String,
                    @BeanProperty val password: String,
                    @BeanProperty val country: String,
                    @BeanProperty val city: String,
                    @BeanProperty val street: String,
                    @BeanProperty val buildingNumber: String) {

    override def toString: String = s"$firstName, $lastName, $dob, $phone, $email, $country, $city, $street, $buildingNumber"
}

