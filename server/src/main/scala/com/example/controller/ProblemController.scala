package com.example.controller

import com.example.exception.{BuildExceptionHandler, EmptyFieldException, InjectionException}
import com.example.model.Problem
import com.example.payload.response.BuildOkResponse
import com.example.service.ProblemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.{CrossOrigin, ExceptionHandler, PostMapping, RequestBody, RequestMapping, ResponseBody, RestController}
import org.springframework.web.bind.annotation.RequestMethod.GET

import javax.validation.Valid



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
    def addProblem(@Valid @RequestBody problem: Problem) : ResponseEntity[_] = {
        println(problem)
        problemService.addProblem(problem)
        BuildOkResponse.createOkResponse
    }

    @ExceptionHandler(Array(classOf[EmptyFieldException]))
    def handleEmptyException(exception: EmptyFieldException): ResponseEntity[_] = {
        val message = BuildExceptionHandler.createErrorMessage(exception)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Array(classOf[InjectionException]))
    def handleInjectionException(exception: InjectionException): ResponseEntity[_] = {
        val message = BuildExceptionHandler.createErrorMessage(exception)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }


}
