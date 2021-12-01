package com.example.service

import com.example.exception.{EmptyFieldException, InjectionException}

import scala.collection.JavaConverters._
import com.example.model.Problem
import org.springframework.stereotype.Service

@Service
class ProblemService {
    var problems = List(
            Problem("Woda", "Brak ciepłej wody", "help me please2!!!!!", "03.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please3!!!!!", "23.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please1!!!!!", "13.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please4!!!!!", "12.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please5!!!!!", "12.11.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please6!!!!!", "11.11.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please7!!!!!", "17.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please8!!!!!", "24.10.2021", "Reymonta, 17", 50.06588, 19.915247)
        );

    def getAllProblems: java.util.List[Problem] = {
        println(problems )
        problems.toList.asJava
    }

    def addProblem(problem: Problem): Unit = {
        if (checkIfStringIsBlank(problem.getAddress) || checkIfStringIsBlank(problem.getCategory) || checkIfStringIsBlank(problem.getDate) || checkIfStringIsBlank(problem.getDescription) || checkIfStringIsBlank(problem.getProblem)) {
            throw EmptyFieldException("Pole nie może być puste, proszę sprawdzić dane i spróbować jeszcze raz!")
        }
        if (checkInjection(problem.getAddress) || checkInjection(problem.getCategory) || checkInjection(problem.getDate) || checkInjection(problem.getDescription) || checkInjection(problem.getProblem)) {
            throw InjectionException("Używasz nieprawidłowych  znaków, proszę sprawdzić dane i spróbować jeszcze raz!")
        }
        problems = problem :: problems
    }

    def checkIfStringIsBlank(value: String): Boolean = {
        value == null || value.isEmpty || value.isBlank
    }

    def checkInjection(value: String): Boolean = {
       value.contains("<") || value.contains(">")
    }

}
