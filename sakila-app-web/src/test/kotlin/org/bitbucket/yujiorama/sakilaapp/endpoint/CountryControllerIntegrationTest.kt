package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Country
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainerProvider

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = [CountryControllerIntegrationTest.TestDatabaseInitializer::class]
)
class CountryControllerIntegrationTest(
        @Autowired private val restTemplate: TestRestTemplate
) {
    class TestDatabaseInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {

            database.start()
            TestPropertyValues.of(
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            ).applyTo(applicationContext.environment)
        }

        companion object {
            val database = PostgreSQLContainerProvider()
                    .newInstance("13-alpine")
                    .withDatabaseName("sakila")
                    .withUsername("postgres")
                    .withPassword("postgres")
        }
    }

    @BeforeAll
    fun setup() {
        println(">> setup")
    }

    @AfterAll
    fun teardown() {
        println(">> teardown")
    }


    @Test
    fun `test for test`() {
        val entities = restTemplate.getForEntity<List<Country>>("/countries")
        assert(entities.statusCode.is2xxSuccessful)
    }
}