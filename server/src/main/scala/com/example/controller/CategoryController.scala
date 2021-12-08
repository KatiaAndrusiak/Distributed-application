package com.example.controller

import com.example.entity.Category
import com.example.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{CrossOrigin, GetMapping, RestController}


@RestController
@CrossOrigin
class CategoryController(@Autowired val categoryRepository: CategoryRepository) {

    @GetMapping(path = Array("/categories"))
    def demo: java.util.List[Category] = categoryRepository.findAll()
}
