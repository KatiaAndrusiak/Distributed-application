package com.example.entity

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonManagedReference}

import java.util
import java.util.{HashSet, Set}
import javax.persistence.{Column, Entity, GeneratedValue, GenerationType, Id, OneToMany, Table}
import scala.beans.BeanProperty

@Entity
@JsonManagedReference
@Table(name = "category")
class Category extends Serializable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ncategoryid")
    @BeanProperty
    var id: Int = _

    @Column(name = "scategoryname")
    @BeanProperty
    var name: String = _

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @BeanProperty
    var problem: util.Set[Problem] = new util.HashSet[Problem]

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @BeanProperty
    var subscriberCategory: util.Set[SubscriberCategory] = new util.HashSet[SubscriberCategory]

    override def toString: String = s"id: $id, category name: $name"

}
