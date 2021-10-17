package com.example.entity

import javax.persistence.{CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, JoinColumn, ManyToOne, Table}
import scala.beans.BeanProperty


@Entity
@Table(name = "subscribercategory")
class SubscriberCategory extends Serializable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nsubscribercategoryid")
    @BeanProperty
    var id: Int = _



    @ManyToOne(cascade = Array(CascadeType.ALL))
    @JoinColumn(name = "ncategoryid")
    @BeanProperty
    var category: Category = null

    @ManyToOne(cascade = Array(CascadeType.ALL))
    @JoinColumn(name = "nsubscriberid")
    @BeanProperty
    var subscriber: Subscriber = null

    override def toString: String = s"subscriber: ${subscriber.toString}, category: ${category.toString}"

}
