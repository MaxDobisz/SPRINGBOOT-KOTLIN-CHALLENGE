package com.example.api.repository

import com.example.api.model.PersonModel
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.LocalDate

@DataJpaTest
class PersonPaginationRepositoryTest {
    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var personPaginationRepository: PersonPaginationRepository

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

        val pageable: Pageable = PageRequest.of(1, 1)

        personRepository.save(person1)
        personRepository.save(person2)

        val personsList : MutableIterable<PersonModel> = personPaginationRepository.findAll(pageable)

        Assertions.assertThat(personsList).hasSize(1)
        Assertions.assertThat(personsList.first().firstName).isEqualTo("Molly")
    }
}