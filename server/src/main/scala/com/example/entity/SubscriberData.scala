package com.example.entity

import javax.persistence.{Column, Entity, Id, JoinColumn, MapsId, OneToOne, Table}
import scala.beans.BeanProperty


@Entity
@Table(name = "subscriberdata")
class SubscriberData extends Serializable() {

    @Id
    @Column(name = "nsubscriberid")
    @BeanProperty
    var id: Int = _

    @Column(name = "semail")
    @BeanProperty
    var email: String = _

    @Column(name = "spassword")
    @BeanProperty
    var password: String = _

    @OneToOne
    @MapsId
    @JoinColumn(name = "nsubscriberid")
    @BeanProperty
    var subscriber: Subscriber = null

    override def toString: String = s"id: $id, email: $email, password: $password, subscriber: ${subscriber.toString}"
}
