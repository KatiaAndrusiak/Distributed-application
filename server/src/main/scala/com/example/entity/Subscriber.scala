package com.example.entity

import java.util
import javax.persistence.{CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, OneToMany, OneToOne, Table}
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
    @BeanProperty
    var subscriberData: SubscriberData = null

    @OneToOne(
        cascade = Array(CascadeType.ALL),
        mappedBy = "subscriber"
    )
    @BeanProperty
    var subscriberInfo: SubscriberInfo = null

    @OneToMany(mappedBy = "subscriber")
    var subscriberCategory: util.Set[SubscriberCategory] = new util.HashSet[SubscriberCategory]

    override def toString: String = s"id: $id, first name: $fname, last name: $lname"
}