package com.example.entity


import javax.persistence.{CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, JoinColumn, ManyToOne, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "problem")
class Problem extends Serializable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nproblemid")
    @BeanProperty
    var id: Int = _

    @ManyToOne(cascade = Array(CascadeType.ALL))
    @JoinColumn(name = "ncategoryid")
    @BeanProperty
    var category: Category = null

    @Column(name = "sproblemname")
    @BeanProperty
    var name: String = _

    override def toString: String = s"id: $id, problem name: $name, category: ${category.toString}"

}
