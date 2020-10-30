package org.bitbucket.yujiorama.sakilaapp.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [PersistenceTest.TestConfig::class])
class PersistenceTest(
    @Autowired private val repository: ActorRepository,
    @Autowired private val staffRepository: StaffRepository,
    @Autowired private val entityManager: TestEntityManager,
) {

    @Configuration
    @EnableAutoConfiguration
    class TestConfig

    @Test
    fun `new Actor Entity`() {
        val expected = Actor().withFirstName("foo").withLastName("bar")
        repository.save(expected)
        val actual = repository.findByFirstNameOrderByFirstName("foo")
        Assertions.assertTrue(actual.isNotEmpty(), "notEmpty")
        Assertions.assertTrue(actual[0].id!! > 0, "id")
        Assertions.assertEquals(expected, actual[0])
    }

    @Test
    fun `new Staff Entity`() {
        val expected = entityManager.persistAndFlush(
            staffRepository.findById(9001)
                .map { it.copy(id = null, firstName = "new", lastName = "staff") }
                .orElseThrow()
        )

        val actual = staffRepository.findById(expected?.id!!)
        Assertions.assertTrue(actual.isPresent, "isPresent")
        Assertions.assertTrue(actual.get().id!! > 0, "id")
        Assertions.assertEquals(expected, actual.get())
    }
}