package com.example.repository

import com.example.entity.Problem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait ProblemRepository extends JpaRepository[Problem, Int] {

}
