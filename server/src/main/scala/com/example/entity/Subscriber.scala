package com.example.entity

import javax.persistence.{CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, OneToOne, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "subscriber")
class Subscriber extends Serializable() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nsubscriberid")
    @BeanProperty
    var id: Int = _

    @Column(name = "sfirstname")
    @BeanProperty
    var fname: String = _

    @Column(name = "slastname")
    @BeanProperty
    var lname: String = _

    @OneToOne(
        cascade = Array(CascadeType.ALL),
        mappedBy = "subscriber"
    )
    var subscriberData: SubscriberData = null

    override def toString: String = s"id: $id, first name: $fname, last name: $lname"
}