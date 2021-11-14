package com.example.model

import scala.beans.BeanProperty

object Problem {
    def apply(category: String, problem: String, description: String,  date: String, address: String, latitude: String, longitude: String) : Problem = new Problem(category, problem, description, date, address, latitude, longitude)
}

class Problem(@BeanProperty val category: String,
              @BeanProperty val problem: String,
              @BeanProperty val description: String,
              @BeanProperty val date: String,
              @BeanProperty val address: String,
              @BeanProperty val latitude: String,
              @BeanProperty val longitude: String) {

    override def toString: String = s"Kategoria: $category, problem: $problem, opis: $description, data: $date, address: $address,  latitude: $latitude, longitude: $longitude"
}
