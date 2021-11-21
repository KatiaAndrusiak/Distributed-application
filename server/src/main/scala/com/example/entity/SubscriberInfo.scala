package com.example.entity

import org.springframework.format.annotation.DateTimeFormat

import java.time.LocalDate
import javax.persistence.{Column, Entity, Id, JoinColumn, MapsId, OneToOne, Table}
import javax.validation.constraints.{NotBlank, NotNull, Pattern, Size}
import scala.beans.BeanProperty


@Entity
@Table(name = "subscriberinfo")
class SubscriberInfo extends Serializable() {

    @Id
    @Column(name = "nsubscriberid")
    @BeanProperty
    var id: Int = _

    @Column(name = "sphone")
    @BeanProperty
    @NotNull
    @NotBlank(message = "Numer telefonu nie może być pusty")
    @Pattern(regexp = "^\\+[48][0-9]{10}$", message = "Numer telefonu musi zaczynać się od +48...")
    @Size(max = 12, message = "Numer telefonu musi zawierac 12 znaków")
    var phone: String = _

    @Column(name = "scountry")
    @BeanProperty
    @NotNull
    @NotBlank(message = "Kraj nie może być pusty")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Kraj musi zawierać tylko litery")
    @Size(min = 2, max = 50, message = "Kraj nie może zawierać więcej niż 50 znaków")
    var country: String = _

    @Column(name = "scity")
    @BeanProperty
    @NotNull
    @NotBlank(message = "Miasto nie może być puste")
    @Pattern(regexp = "^[a-zA-ZAaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż -]*$", message = "Miasto musi zawierać tylko litery")
    @Size(min = 2, max = 50, message = "Miasto nie może zawierać więcej niż 50 znaków")
    var city: String = _

    @Column(name = "sstreet")
    @BeanProperty
    @NotNull
    @NotBlank(message = "Ulica nie może być pusta")
    @Pattern(regexp = "^[a-zA-ZAaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż -]*$", message = "Ulica musi zawierać tylko litery")
    @Size(min = 2, max = 30, message = "Ulica nie może zawierać więcej niż 50 znaków")
    var street: String = _

    @Column(name = "sbuildingnumber")
    @BeanProperty
    @NotBlank(message = "Numer budynku nie może być pusty")
    @Pattern(regexp = "^[0-9]+[A-Za-z]?", message = "")
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

    override def toString: String = s"id: $id, dob: phone: $phone, full address: $country, $city, $street, $buildingNumber latitude: $latitude, longitude: $longitude subscriber: ${subscriber.toString}"

}
