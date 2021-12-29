package com.example.entity

import javax.persistence.{CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, OneToOne, Table}
import javax.validation.constraints.{NotBlank, NotNull, Pattern, Size}
import scala.beans.BeanProperty

@Entity
@Table(name = "subscriber")
class Subscriber extends Serializable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nsubscriberid")
    @BeanProperty
    @NotNull
    var id: Int = _

    @Column(name = "sfirstname")
    @BeanProperty
    @NotNull
    @Size(min = 2, max = 50, message = "Imię - minimalna wymagana długość - 2, maksymalna - 50")
    @NotBlank(message = "Imię nie może być puste")
    @Pattern(regexp = "^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]*$", message = " Imię musi zawierać tylko litery")
    var fname: String = _

    @Column(name = "slastname")
    @BeanProperty
    @NotNull
    @Size(min = 2, max = 50, message = "Nazwisko - minimalna wymagana długość - 2, maksymalna - 50")
    @NotBlank(message = "Nazwisko nie może być puste")
    @Pattern(regexp = "^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]*$", message = "Nazwisko musi zawierać tylko litery")
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

    override def toString: String = s"id: $id, first name: $fname, last name: $lname"
}