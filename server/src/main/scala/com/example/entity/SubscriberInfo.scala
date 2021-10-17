package com.example.entity

import java.time.LocalDate
import javax.persistence.{Column, Entity, Id, JoinColumn, MapsId, OneToOne, Table}
import scala.beans.BeanProperty


@Entity
@Table(name = "subscriberinfo")
class SubscriberInfo extends Serializable() {

    @Id
    @Column(name = "nsubscriberid")
    @BeanProperty
    var id: Int = _

    @Column(name = "dob")
    @BeanProperty
    var dob: LocalDate = _

    @Column(name = "sphone")
    @BeanProperty
    var phone: String = _

    @Column(name = "scountry")
    @BeanProperty
    var country: String = _

    @Column(name = "scity")
    @BeanProperty
    var city: String = _

    @Column(name = "sstreet")
    @BeanProperty
    var street: String = _

    @Column(name = "sbuildingnumber")
    @BeanProperty
    var buildingNumber: String = _

    @Column(name = "nlatitude")
    @BeanProperty
    var latitude: Double = _

    @Column(name = "nlongitude")
    @BeanProperty
    var longitude: Double = _

    @OneToOne
    @MapsId
    @JoinColumn(name = "nsubscriberid")
    @BeanProperty
    var subscriber: Subscriber = null

    override def toString: String = s"id: $id, dob: $dob, phone: $phone, full address: $country, $city, $street, $buildingNumber latitude: $latitude, longitude: $longitude subscriber: ${subscriber.toString}"

}
