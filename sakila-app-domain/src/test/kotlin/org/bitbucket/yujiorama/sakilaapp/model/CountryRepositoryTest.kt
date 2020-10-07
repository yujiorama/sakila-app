package org.bitbucket.yujiorama.sakilaapp.model

import org.bitbucket.yujiorama.sakilaapp.TestRepositoryConfig
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime.now

@ContextConfiguration(classes = [TestRepositoryConfig::class])
@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CountryRepositoryTest(
        @Autowired private val countryRepository: CountryRepository
) {

    @BeforeEach
    fun setup() {
        countryRepository.save(Country("aaa", now()))
    }

    @Order(99)
    @Test
    fun `test for test`() {
        Assertions.assertTrue(countryRepository != null)

        Assertions.assertTrue(countryRepository.findAll() != null)
    }

    @Order(1)
    @Test
    fun `findById`() {

        val actual = countryRepository.findById(1)
        Assertions.assertTrue(actual.isPresent)
        Assertions.assertEquals(1, actual.get().id)
    }

    @Order(2)
    @Test
    fun `findAllByOrderByCountryAsc`() {
        countryRepository.save(Country("ccc", now()))
        countryRepository.save(Country("bbb", now()))

        val actual = countryRepository.findAllByOrderByCountryAsc()
        Assertions.assertTrue(actual.isEmpty().not())
        Assertions.assertEquals("aaa", actual[0].country)
        Assertions.assertEquals("bbb", actual[1].country)
        Assertions.assertEquals("ccc", actual[2].country)
    }
}
