package com.example.api.repository

import com.example.api.model.PersonModel
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
class PersonRepositoryTest {
    @Autowired
    private lateinit var personRepository: PersonRepository

    @Test
    fun personRepositoryFindAll() {
        val person1 = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2003, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        val person2 = PersonModel(
                firstName = "Molly",
                lastName= "Harrison",
                email= "molly@email.com",
                phone= "07577777777",
                dateOfBirth= LocalDate.of(2000, 8, 23),
                age= 23,
                username = "Mo",
                password = "123"
        )

        personRepository.save(person1)
        personRepository.save(person2)

        val personsList : MutableIterable<PersonModel> = personRepository.findAll()

        Assertions.assertThat(personsList).isNotNull()
        Assertions.assertThat(personsList).hasSize(2)
        Assertions.assertThat(personsList.first().lastName).isEqualTo("Kamper")
    }

    @Test
    fun personRepositoryFindById() {
        val person1 = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2003, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        val savedPerson = personRepository.save(person1)

        val personFoundById = personRepository.findById(savedPerson.id).get()

        Assertions.assertThat(personFoundById.id).isGreaterThan(0)
        Assertions.assertThat(personFoundById.firstName).isEqualTo("Joe")
    }

    @Test
    fun personRepositorySave() {
        val person = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2023, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        val savedPerson = personRepository.save(person)

        Assertions.assertThat(savedPerson).isNotNull()
        Assertions.assertThat(savedPerson.id).isGreaterThan(0)
    }

    @Test
    fun personRepositoryDeleteById() {
        val person = PersonModel(
                firstName = "Joe",
                lastName= "Kamper",
                email= "joe@email.com",
                phone= "07588888888",
                dateOfBirth= LocalDate.of(2023, 8, 23),
                age= 20,
                username = "Joe",
                password = "123"
        )

        val savedPerson = personRepository.save(person)

        personRepository.deleteById(savedPerson.id)

        Assertions.assertThat(savedPerson.id).isGreaterThan(0)
        Assertions.assertThat(personRepository.findById(savedPerson.id)).isEmpty
    }
}
