package com.example.service

import com.example.exception.{EmptyFieldException, InjectionException, NoSuchProblemException, ProblemWasAcceptByAnotherSubscriberException}

import scala.collection.JavaConverters._
import com.example.model.Problem
import com.example.payload.response.BuildOkResponse
import com.example.repository.{CategoryRepository, ProblemRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProblemService(@Autowired problemRepository: ProblemRepository,
                     @Autowired categoryRepository: CategoryRepository) {

    var problems = List(
            Problem("Woda", "Brak ciepłej wody", "help me please2!!!!!", "03.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please3!!!!!", "23.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please1!!!!!", "13.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please4!!!!!", "12.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please5!!!!!", "12.11.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please6!!!!!", "11.11.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please7!!!!!", "17.10.2021", "Reymonta, 17", 50.06588, 19.915247),
            Problem("Woda", "Brak ciepłej wody", "help me please8!!!!!", "24.10.2021", "Reymonta, 17", 50.06588, 19.915247)
            )

    def getAllProblems(categories: String): java.util.List[Problem] = {
        val splitedCategories: Array[String] = categories.split(",")
        val filtredProblems = problems.filter(p => splitedCategories.contains(p.getCategory))
        println(filtredProblems)
        filtredProblems.toList.asJava
    }

    def deleteProblemIfAccepted(problem: Problem): ResponseEntity[_] = {
        if (!problems.exists(p => checkIfProblemExist(p, problem))) {
            throw ProblemWasAcceptByAnotherSubscriberException("Problem został zaakceptowany przez inego użytkownika, wybierz inny problem!")
        }
        problems = problems.filter(p => !checkIfProblemExist(p, problem))
        println(problems)
        BuildOkResponse.createOkResponse
    }

    def checkIfProblemExist(p: Problem, problem: Problem): Boolean = {
        p.getProblem.equals(problem.getProblem) && p.getCategory.equals(problem.getCategory) && p.getDate.equals(problem.getDate) && p.getAddress.equals(problem.getAddress) && p.getDescription.equals(problem.getDescription)
    }

    def addProblem(problem: Problem): Unit = {
        if (checkIfStringIsBlank(problem.getAddress) || checkIfStringIsBlank(problem.getCategory) || checkIfStringIsBlank(problem.getDate) || checkIfStringIsBlank(problem.getDescription) || checkIfStringIsBlank(problem.getProblem)) {
            throw EmptyFieldException("Pole nie może być puste, proszę sprawdzić dane i spróbować jeszcze raz!")
        }
        if (checkInjection(problem.getAddress) || checkInjection(problem.getCategory) || checkInjection(problem.getDate) || checkInjection(problem.getDescription) || checkInjection(problem.getProblem)) {
            throw InjectionException("Używasz nieprawidłowych  znaków, proszę sprawdzić dane i spróbować jeszcze raz!")
        }
        if (!problemRepository.existsByName(problem.getProblem)) {
            throw NoSuchProblemException("Problem nie istnieje!")
        }

        if (!categoryRepository.existsByName(problem.getCategory)) {
            throw NoSuchProblemException("Kategoria nie istnieje!")
        }
        problems = problem :: problems
        deleteProblemIfAccepted(problem)
    }

    def checkIfStringIsBlank(value: String): Boolean = {
        value == null || value.isEmpty || value.isBlank
    }

    def checkInjection(value: String): Boolean = {
       value.contains("<") || value.contains(">")
    }

}
