package com.example.api.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table
data class PersonModel(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        var firstName: String,
        var lastName: String,
        var email: String,
        var phone: String,
        var dateOfBirth: LocalDate,
        var age: Int,
        @Column(unique = true)
        var username: String,
        var password: String
)
