package com.example.api.repository

import com.example.api.model.PersonModel
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<PersonModel, Long>