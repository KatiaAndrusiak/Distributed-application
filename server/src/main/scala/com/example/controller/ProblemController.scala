package com.example.controller

import com.example.model.Problem
import com.example.service.ProblemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RequestMapping, RequestMethod, ResponseBody, RestController}
import org.springframework.web.bind.annotation.RequestMethod.GET



@RestController
class ProblemController(@Autowired val problemService: ProblemService) {

    @GetMapping(path = Array("/"))
    def demo: String = "Hello Scala"

    @RequestMapping(value = Array("/problems"), method = Array(GET))
    @ResponseBody
    def getAllProblems: List[Problem] = problemService.getAllProblems

    @PostMapping(path = Array("/problems"))
    def addProblem(@RequestBody problem: Problem): Unit = problemService.addProblem(problem)


}
