package com.example.entity

import com.example.model.ERole

import javax.persistence._
import scala.beans.BeanProperty


@Entity
@Table(name = "roles")
class Role extends Serializable() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @BeanProperty
    var id: Int = _

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @BeanProperty
    var name: ERole = _
}
