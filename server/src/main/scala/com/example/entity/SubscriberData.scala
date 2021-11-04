package com.example.entity

import javax.persistence.{Column, Entity, Id, JoinColumn, MapsId, OneToOne, Table}
import javax.validation.constraints.{Email, NotBlank, NotNull, Pattern, Size}
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
    @NotNull
    @Size(max = 50, message = "Email nie może zawierać więcej niż 50 znaków")
    @Email
    @NotBlank(message = "Email nie może być pusty")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email musi być w postaci *@*.*")
    var email: String = _

    @Column(name = "spassword")
    @BeanProperty
    @NotNull
    @NotBlank(message = "Hasło nie może być puste")
    @Size(min = 6, max = 50, message = "Hasło - minimalna wymagana długość - 6, maksymalna - 40")
    var password: String = _

    @OneToOne
    @MapsId
    @JoinColumn(name = "nsubscriberid")
    @BeanProperty
    var subscriber: Subscriber = null

    override def toString: String = s"id: $id, email: $email, password: $password, subscriber: ${subscriber.toString}"
}
