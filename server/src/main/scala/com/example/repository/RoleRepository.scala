package com.example.repository

import com.example.entity.Role
import com.example.model.ERole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait RoleRepository extends JpaRepository[Role, Int] {
    def findByName(name: ERole): Option[Role]
}
