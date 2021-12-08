package com.example

import com.example.entity.Category
import com.example.repository.CategoryRepository
import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles(Array("test"))
class CategoryTest {

    @Autowired
    val em: TestEntityManager = null

    @Autowired
    val categoryRepository: CategoryRepository = null

    @Test
    def shouldReturnCategory(): Unit = {
        // given
        val id: Int = 1

        //when
        val category: Category = categoryRepository.findById(id).orElseThrow(() => new NullPointerException)

        //then
        Assert.assertEquals(1, category.getId)
        Assert.assertEquals("Woda", category.getName)
    }

    @Test(expected = classOf[NullPointerException])
    def shouldThrowExceptionWhenCategoryNotExist(): Unit = {
        // given
        val id: Int = 7

        // when
        val category: Category = categoryRepository.findById(id).orElseThrow(() => new NullPointerException)

        // then
    }
}
