package com.example.service

import scala.collection.JavaConverters._
import com.example.model.Problem
import org.springframework.stereotype.Service

@Service
class ProblemService {
    var problems = List(Problem("woda", "Brak ciepłej wody", "13.10.2021", "50.06588", "19.915247"));

    def getAllProblems: java.util.List[Problem] = {
        println(problems)
        problems.toList.asJava
    }

    def addProblem(problem: Problem): Unit = {
        problems = problem :: problems
    }

}
