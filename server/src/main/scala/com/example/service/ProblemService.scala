package com.example.service

import scala.collection.JavaConverters._
import com.example.model.Problem
import org.springframework.stereotype.Service

@Service
class ProblemService {
    var problems = List(Problem("Woda", "Brak ciepłej wody", "help me please!!!!!", "13.10.2021", "Reymonta, 17", "50.06588", "19.915247"));

    def getAllProblems: java.util.List[Problem] = {
        println(problems )
        problems.toList.asJava
    }

    def addProblem(problem: Problem): Unit = {
        problems = problem :: problems
    }

}
