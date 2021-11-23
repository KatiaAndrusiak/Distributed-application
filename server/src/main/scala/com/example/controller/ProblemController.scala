package com.example.controller

import com.example.model.Problem
import com.example.service.ProblemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.{CrossOrigin, PostMapping, RequestBody, RequestMapping, ResponseBody, RestController}
import org.springframework.web.bind.annotation.RequestMethod.GET



@RestController
@CrossOrigin
@RequestMapping(Array("/request"))
class ProblemController(@Autowired val problemService: ProblemService) {

    @RequestMapping(value = Array("/problems"), method = Array(GET))
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    def getAllProblems: java.util.List[Problem] = problemService.getAllProblems

    @PostMapping(path = Array("/problems"))
    @PreAuthorize("permitAll()")
    def addProblem(@RequestBody problem: Problem): Unit = problemService.addProblem(problem)


}
