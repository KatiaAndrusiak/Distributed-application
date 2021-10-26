package com.example.controller

import com.example.model.Problem
import com.example.service.ProblemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{CrossOrigin, GetMapping, PostMapping, RequestBody, RequestMapping, RequestMethod, ResponseBody, RestController}
import org.springframework.web.bind.annotation.RequestMethod.GET



@RestController
@CrossOrigin
class ProblemController(@Autowired val problemService: ProblemService) {

    @GetMapping(path = Array("/"))
    def demo: String = "Hello Scala"

    @RequestMapping(value = Array("/problems"), method = Array(GET))
    @ResponseBody
    def getAllProblems: java.util.List[Problem] = problemService.getAllProblems

    @PostMapping(path = Array("/problems"))
    def addProblem(@RequestBody problem: Problem): Unit = problemService.addProblem(problem)


}
