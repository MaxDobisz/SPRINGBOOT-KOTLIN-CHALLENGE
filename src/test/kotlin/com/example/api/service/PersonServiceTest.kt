package com.example.api.service

import com.example.api.model.PersonModel
import com.example.api.repository.PersonPaginationRepository
import com.example.api.repository.PersonRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.assertj.core.api.Assertions
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class PersonServiceTest {
    @Mock
    private  lateinit var personRepository: PersonRepository

    @Mock
    private  lateinit var  personPaginationRepository: PersonPaginationRepository

    @InjectMocks
    private lateinit var personService: PersonService

    @Test
    fun getAllPersons(){
        val persons: Page<PersonModel> = Mockito.mock(Page::class.java) as Page<PersonModel>

        Mockito.`when`(personPaginationRepository.findAll(Mockito.any(Pageable::class.java))).thenReturn(persons)

        val savedPersons = personService.getAllPersons(1,1)

        Assertions.assertThat(savedPersons).isNotNull
    }

    @Test
    fun getPersonById (){
        val person = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2003, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        Mockito.`when`(personRepository.findById(1)).thenReturn(Optional.ofNullable(person))

        val savedPerson = personService.getPersonById(1)

        Assertions.assertThat(savedPerson).isNotNull
        Assertions.assertThat(savedPerson).isEqualTo(person)
    }

    @Test
    fun createPerson() {
        val person = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2003, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        Mockito.`when`(personRepository.save(Mockito.any(PersonModel::class.java))).thenReturn(person)

        val savedPerson = personService.createPerson(person)

        Assertions.assertThat(savedPerson).isNotNull()
        Assertions.assertThat(savedPerson.body).isEqualTo(person)
        Assertions.assertThat(savedPerson.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    @Test
    fun updatePerson() {
        val person = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2003, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        Mockito.`when`(personRepository.findById(1)).thenReturn(Optional.ofNullable(person))
        Mockito.`when`(personRepository.save(Mockito.any(PersonModel::class.java))).thenReturn(person)

        val savedPerson = personService.updatePerson(1, person)

        Assertions.assertThat(savedPerson).isNotNull()
        Assertions.assertThat(savedPerson.body!!.firstName).isEqualTo("Joe")
        Assertions.assertThat(savedPerson.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun deletePerson() {
        val person = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2003, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        Mockito.`when`(personRepository.findById(1)).thenReturn(Optional.ofNullable(person))

        val result = personService.deletePerson(1)

        Assertions.assertThat(result.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }
}

