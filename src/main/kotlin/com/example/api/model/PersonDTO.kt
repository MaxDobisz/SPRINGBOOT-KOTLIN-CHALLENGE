package com.example.api.model

import java.time.LocalDate

data class PersonDTO(
        val id: Long,
        var firstName: String,
        var lastName: String,
        var email: String,
        var phone: String,
        var dateOfBirth: LocalDate,
        var age: Int,
)
