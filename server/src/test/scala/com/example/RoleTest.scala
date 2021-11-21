package com.example

import com.example.model.ERole
import com.example.repository.RoleRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.{DataJpaTest, TestEntityManager}
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class RoleTest {

    @Autowired
    val em: TestEntityManager = null

    @Autowired
    val roleRepository: RoleRepository = null

    @Test
    def shouldReturnRole(): Unit = {
        //given
        val admin = ERole.ROLE_ADMIN
        val user = ERole.ROLE_USER

        //when
        val adminRole = roleRepository.findByName(admin).getOrElse(throw new NullPointerException)
        val userRole = roleRepository.findByName(user).getOrElse(throw new NullPointerException)

        //then
        assertEquals("ROLE_ADMIN", adminRole.getName.toString)

        //then
        assertEquals("ROLE_USER", userRole.getName.toString)

    }

}
