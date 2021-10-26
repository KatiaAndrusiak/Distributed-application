package com.example.repository

import com.example.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait CategoryRepository extends JpaRepository[Category, Int] {


}
