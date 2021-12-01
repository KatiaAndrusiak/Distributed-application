package com.example.model

import javax.validation.Valid
import scala.beans.BeanProperty
import javax.validation.constraints.{NotBlank, NotNull, Pattern, Size}

object Problem {
    def apply(category: String, problem: String, description: String,  date: String, address: String, latitude: Double, longitude: Double) : Problem = new Problem(category, problem, description, date, address, latitude, longitude)
}

class Problem(@BeanProperty val category: String,
              @BeanProperty val problem: String,
              @BeanProperty val description: String,
              @BeanProperty val date: String,
              @BeanProperty val address: String,
              @BeanProperty val latitude: Double,
              @BeanProperty val longitude: Double) {

    override def toString: String = s"Kategoria: $category, problem: $problem, opis: $description, data: $date, address: $address,  latitude: $latitude, longitude: $longitude"
}
