package com.example.api.service

import org.springframework.stereotype.Service
import com.example.api.repository.PersonRepository
import com.example.api.model.PersonModel
import com.example.api.model.PersonDTO
import com.example.api.repository.PersonPaginationRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.HttpStatus
import jakarta.persistence.criteria.Predicate
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException

@Service
class PersonService(
        val personRepository: PersonRepository,
        val personPaginationRepository: PersonPaginationRepository
) {
    fun getAllPersons(page: Int?,size: Int?) : Iterable<PersonModel> {
        return if (page != null && size != null) {
            val pageable: Pageable = PageRequest.of(page, size)
            personPaginationRepository.findAll(pageable).content
        } else {
            personRepository.findAll()
        }
    }

    fun getPersonById(id: Long): PersonModel {
        return personRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Person with ID $id not found")
        }
    }

    fun createPerson(person: PersonModel): ResponseEntity<PersonModel> {
        val personsList : Iterable<PersonModel> = personRepository.findAll()
        val isUserNameTaken : Boolean = personsList.any { it.username == person.username }

        if (isUserNameTaken) throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Username ${person.username} is already taken"
        )

        val createdPerson : PersonModel = personRepository.save(person)

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson)
    }

    fun updatePerson(id: Long, updatedPerson: PersonModel): ResponseEntity<PersonModel> {
        personRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Person with ID $id not found")
        }

        personRepository.save(updatedPerson)

        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson)
    }

    fun deletePerson(id: Long): ResponseEntity<Nothing> {
        personRepository.findById(id).orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Person with ID $id not found")
        }

        personRepository.deleteById(id)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    fun getFilteredPersons(firstName: String?, lastName: String?, age: Int?): List<PersonDTO> {
        if(firstName.isNullOrBlank() && lastName.isNullOrBlank() && age == null) {
            val persons: Iterable<PersonModel> = personRepository.findAll()
            val personsLimitedProperties: List<PersonDTO> = persons.map {
                person -> PersonDTO(
                    person.id,
                    person.firstName,
                    person.lastName,
                    person.email,
                    person.phone,
                    person.dateOfBirth,
                    person.age
            )
            }

            return personsLimitedProperties
        }

        val specification: Specification<PersonModel> = Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (!firstName.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%$firstName%"))
            }

            if (!lastName.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%$lastName%"))
            }

            if (age != null) predicates.add(criteriaBuilder.equal(root.get<Int>("age"), age))

            criteriaBuilder.and(*predicates.toTypedArray())
        }

        val filteredPersons : List<PersonModel?> = personPaginationRepository.findAll(specification)

        if (filteredPersons.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No persons found matching the specified criteria")

        val filteredPersonsLimitedProperties: List<PersonDTO> = filteredPersons.map {
            person -> person?.let {
                PersonDTO(
                        it.id,
                        person.firstName,
                        person.lastName,
                        person.email,
                        person.phone,
                        person.dateOfBirth,
                        person.age
                )
            }!!
        }

        return filteredPersonsLimitedProperties
    }
}