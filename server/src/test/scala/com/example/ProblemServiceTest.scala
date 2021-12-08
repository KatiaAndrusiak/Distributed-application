package com.example

import com.example.exception.{EmptyFieldException, InjectionException, NoSuchCategoryException, NoSuchProblemException, ProblemWasAcceptByAnotherSubscriberException}
import com.example.model.Problem
import com.example.service.ProblemService
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(classOf[SpringRunner])
@ActiveProfiles(Array("test"))
class ProblemServiceTest {

    @Autowired
    val problemService: ProblemService = null

    @Test
    def shouldAddProblemAndThenGetAllProblems: Unit = {
        //given
        val waterProblem = new Problem("Woda", "Brak ciepłej wody", "Brak wody", "2021-12-08 18:17:06", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)
        val fireProblem =  new Problem("Ogień", "Płonący kosz na smieci", "Płonący kosz", "2021-12-08 18:55:10", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)
        val categories = "Woda, Ogień"

        //when
        problemService.addProblem(waterProblem)
        problemService.addProblem(fireProblem)
        val problems = problemService.getAllProblems(categories)

        //then
        Assert.assertFalse(problems.isEmpty)
    }

    @Test
    def shouldAddProblemAndThenDelete: Unit = {
        //given
        val waterProblem = new Problem("Woda", "Brak ciepłej wody", "Brak wody", "2021-12-08 18:17:06", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)
        val fireProblem =  new Problem("Ogień", "Płonący kosz na smieci", "Płonący kosz", "2021-12-08 18:55:10", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)
        val categories = "Woda, Ogień"

        //when
        problemService.addProblem(waterProblem)
        problemService.addProblem(fireProblem)
        problemService.deleteProblemIfAccepted(waterProblem)
        problemService.deleteProblemIfAccepted(fireProblem)
        val problems = problemService.getAllProblems(categories)

        //then
        Assert.assertTrue(problems.isEmpty)
    }

    @Test(expected = classOf[ProblemWasAcceptByAnotherSubscriberException])
    def shouldThrowProblemWasAcceptByAnotherSubscriberException: Unit = {
        //given
        val waterProblem = new Problem("Woda", "Brak ciepłej wody", "Brak wody", "2021-12-08 18:17:25", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)
        val fireProblem =  new Problem("Ogień", "Płonący kosz na smieci", "Płonący kosz", "2021-12-08 18:55:10", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)
        val categories = "Woda, Ogień"


        //when
        problemService.deleteProblemIfAccepted(waterProblem)
        problemService.deleteProblemIfAccepted(fireProblem)

        //then
    }

    @Test(expected = classOf[EmptyFieldException])
    def shouldThrowEmptyFieldException: Unit = {
        //given
        val waterProblem = new Problem("Woda", "", "Brak wody", "2021-12-08 18:17:06", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)

        //when
        problemService.addProblem(waterProblem)

        //then
    }

    @Test(expected = classOf[NoSuchProblemException])
    def shouldThrowNoSuchProblemException: Unit = {
        //given
        val waterProblem = new Problem("Woda", "Brak ciepłej", "Brak wody", "2021-12-08 18:17:06", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)

        //when
        problemService.addProblem(waterProblem)

        //then
    }

    @Test(expected = classOf[NoSuchCategoryException])
    def shouldThrowNoSuchCategoryException: Unit = {
        //given
        val waterProblem = new Problem("Komunikacja", "Brak ciepłej wody", "Brak wody", "2021-12-08 18:17:06", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)

        //when
        problemService.addProblem(waterProblem)

        //then
    }

    @Test(expected = classOf[InjectionException])
    def shouldThrowInjectionException: Unit = {
        //given
        val waterProblem = new Problem("Woda", "Brak ciepłej wody", "<Brak wody>", "2021-12-08 18:17:06", "Władysława Reymonta 19A, Kraków", 50.06587950, 19.91524670)

        //when
        problemService.addProblem(waterProblem)

        //then
    }

}
