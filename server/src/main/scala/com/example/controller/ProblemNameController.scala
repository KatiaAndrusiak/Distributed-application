package com.example.controller

import com.example.entity.Problem
import com.example.repository.ProblemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.{CrossOrigin, RequestMapping, ResponseBody, RestController}


@RestController
@CrossOrigin
class ProblemNameController(@Autowired problemRepository: ProblemRepository) {

    @RequestMapping(value = Array("/problems"), method = Array(GET))
    @ResponseBody
    def getProblemNames: java.util.List[Problem] = {
        problemRepository.findAll
    }

}
