package com.example.controller

import com.example.model.Problem
import com.example.payload.response.BuildOkResponse
import com.example.service.ProblemService
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
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

    @PostMapping(path = Array("/problems"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
    @PreAuthorize("permitAll()")
    def addProblem(@RequestBody problem: Problem) : ResponseEntity[_] = {
        problemService.addProblem(problem)
        BuildOkResponse.createOkResponse
    }


}
