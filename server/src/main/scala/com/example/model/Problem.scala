package com.example.model

import scala.beans.BeanProperty

object Problem {
    def apply(category: String, name: String, date: String, latitude: String, longitude: String) : Problem = new Problem(category, name, date, latitude, longitude)
}

class Problem(@BeanProperty val category: String,
              @BeanProperty val name: String,
              @BeanProperty val date: String,
              @BeanProperty val latitude: String,
              @BeanProperty val longitude: String) {

    override def toString: String = s"category: $category, problem: $name, data: $date, latitude: $latitude, longitude: $longitude"
}
