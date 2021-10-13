package com.example.model

object Problem {
    def apply(category: String, name: String, date: String, latitude: String, longitude: String) : Problem = new Problem(category, name, date, latitude, longitude)
}

class Problem(category: String, name: String, date: String, latitude: String, longitude: String) {

    override def toString: String = s"category: $category, problem: $name, data: $date, latitude: $latitude, longitude: $longitude"
}
