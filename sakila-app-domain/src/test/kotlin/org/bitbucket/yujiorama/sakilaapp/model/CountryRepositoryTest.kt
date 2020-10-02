package org.bitbucket.yujiorama.sakilaapp.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.data.relational.core.dialect.PostgresDialect
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime.now

@ContextConfiguration(classes = [CountryRepositoryTest.TestConfig::class])
@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CountryRepositoryTest(
        @Autowired private val countryRepository: CountryRepository
) {

    @ActiveProfiles("test")
    @Configuration
    @EnableJdbcRepositories
    class TestConfig : AbstractJdbcConfiguration() {
        override fun jdbcDialect(operations: NamedParameterJdbcOperations): Dialect = PostgresDialect.INSTANCE
    }

    @BeforeEach
    fun setup() {
        countryRepository.save(Country("aaa", now()))
    }

    @Test
    fun `test for test`() {
        Assertions.assertTrue(countryRepository != null)

        Assertions.assertTrue(countryRepository.findAll() != null)
    }

    @Test
    fun `read`() {

        val actual = countryRepository.findById(1)
        Assertions.assertTrue(actual.isPresent)
        Assertions.assertEquals(1, actual.get().id)
    }
}