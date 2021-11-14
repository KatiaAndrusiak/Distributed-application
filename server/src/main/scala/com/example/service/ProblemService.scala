package com.example.service

import scala.collection.JavaConverters._
import com.example.model.Problem
import org.springframework.stereotype.Service

@Service
class ProblemService {
    var problems = List(
            Problem("Woda", "Brak ciepłej wody", "help me please1!!!!!", "13.10.2021", "Reymonta, 17", "50.06588", "19.915247"),
            Problem("Woda", "Brak ciepłej wody", "help me please2!!!!!", "03.10.2021", "Reymonta, 17", "50.06588", "19.915247"),
            Problem("Woda", "Brak ciepłej wody", "help me please3!!!!!", "23.10.2021", "Reymonta, 17", "50.06588", "19.915247"),
            Problem("Woda", "Brak ciepłej wody", "help me please4!!!!!", "12.10.2021", "Reymonta, 17", "50.06588", "19.915247"),
            Problem("Woda", "Brak ciepłej wody", "help me please5!!!!!", "12.11.2021", "Reymonta, 17", "50.06588", "19.915247"),
            Problem("Woda", "Brak ciepłej wody", "help me please6!!!!!", "11.11.2021", "Reymonta, 17", "50.06588", "19.915247"),
            Problem("Woda", "Brak ciepłej wody", "help me please7!!!!!", "17.10.2021", "Reymonta, 17", "50.06588", "19.915247"),
            Problem("Woda", "Brak ciepłej wody", "help me please8!!!!!", "24.10.2021", "Reymonta, 17", "50.06588", "19.915247")
        );

    def getAllProblems: java.util.List[Problem] = {
        println(problems )
        problems.toList.asJava
    }

    def addProblem(problem: Problem): Unit = {
        problems = problem :: problems
    }

}
