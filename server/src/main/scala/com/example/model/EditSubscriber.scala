package com.example.model

import scala.beans.BeanProperty

object EditSubscriber {
    def apply(name: String,
              lastName: String,
              number: String,
              email: String,
              oldPassword: String,
              newPassword: String,
              country: String,
              city: String,
              address: String,
              category: java.util.List[String],
              role: java.util.Set[String]) : EditSubscriber = new EditSubscriber(name, lastName, number, email, oldPassword, newPassword, country,city, address, category, role)
}

class EditSubscriber(@BeanProperty val firstName: String,
                    @BeanProperty val lastName: String,
                    @BeanProperty val phone: String,
                    @BeanProperty val email: String,
                    @BeanProperty val oldPassword: String,
                    @BeanProperty val newPassword: String,
                    @BeanProperty val country: String,
                    @BeanProperty val city: String,
                    @BeanProperty val address: String,
                    @BeanProperty val category: java.util.List[String],
                    @BeanProperty val role: java.util.Set[String]) {

    override def toString: String = s"$firstName, $lastName, $phone, $email, $country, $city, $address, $category"
}
