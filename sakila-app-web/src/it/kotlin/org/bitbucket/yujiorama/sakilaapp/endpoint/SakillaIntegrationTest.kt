package org.bitbucket.yujiorama.sakilaapp.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import org.bitbucket.yujiorama.sakilaapp.model.Actor
import org.bitbucket.yujiorama.sakilaapp.model.Category
import org.bitbucket.yujiorama.sakilaapp.model.Country
import org.bitbucket.yujiorama.sakilaapp.model.Language
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.LinkedMultiValueMap
import org.testcontainers.containers.PostgreSQLContainerProvider

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = [SakillaIntegrationTest.TestDatabaseInitializer::class]
)
class SakillaIntegrationTest(
        @Autowired private val restTemplate: TestRestTemplate,
        @Autowired private val objectMapper: ObjectMapper
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
    fun `test countries`() {
        val res = restTemplate.getForEntity<List<Country>>("/countries")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful)
    }

    @Test
    fun `read Country(United States)`() {
        val res = restTemplate.getForEntity<Country>("/countries/103")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful)
        val country = res.body
        Assertions.assertEquals(103, country?.id)
        Assertions.assertEquals("United States", country?.country)
    }

    @Test
    fun `read Actor(Judy Dean)`() {
        val res = restTemplate.getForEntity<Actor>("/actors/35")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful)
        val actor = res.body
        Assertions.assertEquals(35, actor?.id)
        Assertions.assertEquals("judy", actor?.firstName?.toLowerCase())
        Assertions.assertEquals("dean", actor?.lastName?.toLowerCase())
    }

    @Test
    fun `create Language(Spanish)`() {
        val res = restTemplate.postForEntity<Language>("/languages", Language("Spanish"), Language::class.java)
        Assertions.assertTrue(res.statusCode.is2xxSuccessful)
        val language = res.body
        Assertions.assertNotNull(language?.id)
        Assertions.assertEquals("spanish", language?.name?.toLowerCase())
    }

    @Test
    fun `update Category(Sci-Fi to SF)`() {
        try {
            val getResponse = restTemplate.getForEntity<Category>("/categories/14")
            Assertions.assertTrue(getResponse.statusCode.is2xxSuccessful)
            val category = getResponse.body
            val request = category?.copy(name = "SF")?.let {
                val headers = LinkedMultiValueMap<String, String>()
                headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, listOf(MediaType.APPLICATION_JSON_VALUE))
                HttpEntity(it, headers)
            }
            restTemplate.put("/categories/${category?.id}", request)
        } catch (e: Exception) {
            Assertions.fail<Void>(e.message)
        }

        val res = restTemplate.getForEntity<Category>("/categories/14")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful)
        val newCategory = res.body
        Assertions.assertEquals(14, newCategory?.id)
        Assertions.assertEquals("SF", newCategory?.name)
    }

    @Test
    fun `delete Staff(Jon Stephens)`() {
        try {
            restTemplate.delete("/staffs/2")
        } catch (e: Exception) {
            Assertions.fail<Void>(e.message)
        }

        val res = restTemplate.getForEntity<String>("/staffs/2")
        Assertions.assertEquals(HttpStatus.valueOf(res.statusCodeValue), HttpStatus.NOT_FOUND)
    }
}
