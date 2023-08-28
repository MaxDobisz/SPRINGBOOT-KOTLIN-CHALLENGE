package com.example.api.controller

import com.example.api.model.PersonDTO
import com.example.api.model.PersonModel
import com.example.api.service.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/persons")
class CRUDController (val service: PersonService) {
    @GetMapping
    fun getAllPersons(
            @RequestParam(required = false) page: Int?,
            @RequestParam(required = false) size: Int?,
    ): Iterable<PersonModel> = service.getAllPersons(page,size)

    @GetMapping("/{id}")
    fun getPersonById(@PathVariable id: Long): PersonModel = service.getPersonById(id)

    @PostMapping
    fun createPerson(@RequestBody personModel: PersonModel): ResponseEntity<PersonModel> = service.createPerson(personModel)

    @PutMapping("/{id}")
    fun updatePerson(
            @PathVariable id: Long,
            @RequestBody updatedPerson: PersonModel
    ): ResponseEntity<PersonModel> = service.updatePerson(id, updatedPerson)

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable id: Long): ResponseEntity<Nothing> = service.deletePerson(id)

    @GetMapping("/filter")
    fun getFilteredPersons(
            @RequestParam(name = "firstName", required = false) firstName: String?,
            @RequestParam(name = "lastName", required = false) lastName: String?,
            @RequestParam(name = "age", required = false) age: Int?
    ): List<PersonDTO> = service.getFilteredPersons(firstName,lastName,age)
}