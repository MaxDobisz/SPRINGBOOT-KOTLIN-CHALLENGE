package com.example.api.repository

import com.example.api.model.PersonModel
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.PagingAndSortingRepository

interface PersonPaginationRepository : PagingAndSortingRepository<PersonModel, Long> {
    fun findAll(specification: Specification<PersonModel>): List<PersonModel>
}