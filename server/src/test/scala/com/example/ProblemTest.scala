package com.example

import com.example.entity.Problem
import com.example.repository.ProblemRepository
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ProblemTest {

    @Autowired
    val em: TestEntityManager = null

    @Autowired
    val problemRepository: ProblemRepository = null

    @Test
    def shouldReturnCategory(): Unit = {
        // given
        val id: Int = 1

        //when
        val problem: Problem = problemRepository.findById(id).orElseThrow(() => new NullPointerException)
        println(problemRepository.findAll)

        //then
        Assert.assertEquals(1, problem.getId)
        Assert.assertEquals("Brak ciepłej wody", problem.getName)
    }

    @Test(expected = classOf[NullPointerException])
    def shouldThrowExceptionWhenProblemNotExist(): Unit = {
        // given
        val id: Int = 10000

        // when
        val problem: Problem = problemRepository.findById(id).orElseThrow(() => new NullPointerException)

        // then
    }

}
