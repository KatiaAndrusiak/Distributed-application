package com.example.entity

import javax.persistence.{Column, Entity, GeneratedValue, GenerationType, Id, Table}

@Entity
@Table(name = "subscriber")
class Subscriber extends Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nsubscriberid")
    var id: Long = _

    @Column(name = "semail")
    private var name = null

    @Column(name = "price")
    private var price = 0
}
