package org.bitbucket.yujiorama.sakilaapp

import org.bitbucket.yujiorama.sakilaapp.model.*
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
    fun `test countries`() {
        val res = restTemplate.getForEntity<List<CountryEntity>>("/countries")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
    }

    @Test
    fun `read Film(PG)`() {
        val res = restTemplate.getForEntity<FilmEntity>("/films/1")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val film = res.body!!
        Assertions.assertEquals(1, film.id)
        Assertions.assertEquals("ACADEMY DINOSAUR", film.title)
        Assertions.assertEquals(RatingEnum.PG, film.rating)
    }

    @Test
    fun `read Film(PG-13)`() {
        val res = restTemplate.getForEntity<FilmEntity>("/films/7")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val film = res.body!!
        Assertions.assertEquals(7, film.id)
        Assertions.assertEquals("AIRPLANE SIERRA", film.title)
        Assertions.assertEquals(RatingEnum.PG_13, film.rating)
    }

    @Test
    fun `read Country(United States)`() {
        val res = restTemplate.getForEntity<CountryEntity>("/countries/103")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val country = res.body
        Assertions.assertEquals(103, country?.id)
        Assertions.assertEquals("United States", country?.country)
    }

    @Test
    fun `read Actor(Judy Dean)`() {
        val res = restTemplate.getForEntity<ActorEntity>("/actors/35")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val actor = res.body
        Assertions.assertEquals(35, actor?.id)
        Assertions.assertEquals("judy", actor?.firstName?.toLowerCase())
        Assertions.assertEquals("dean", actor?.lastName?.toLowerCase())
    }

    @Test
    fun `create Language(Spanish)`() {
        val res = restTemplate.postForEntity<LanguageEntity>("/languages", LanguageEntity().withName("Spanish"), LanguageEntity::class.java)
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val language = res.body
        Assertions.assertNotNull(language?.id)
        Assertions.assertEquals("spanish", language?.name?.toLowerCase())
    }

    @Test
    fun `update Category(Sci-Fi to SF)`() {
        try {
            val res = restTemplate.getForEntity<CategoryEntity>("/categories/14")
            Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
            val category = res.body
            val request = category?.withName("SF")?.let {
                val headers = LinkedMultiValueMap<String, String>()
                headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, listOf(MediaType.APPLICATION_JSON_VALUE))
                HttpEntity(it, headers)
            }
            restTemplate.put("/categories/${category?.id}", request)
        } catch (e: Exception) {
            Assertions.fail<Void>(e.message)
        }

        val res = restTemplate.getForEntity<CategoryEntity>("/categories/14")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val newCategory = res.body
        Assertions.assertEquals(14, newCategory?.id)
        Assertions.assertEquals("SF", newCategory?.name)
    }

    @Test
    fun `read and write Staff picture`() {
        val pictureBytes = byteArrayOf(1, 1, 2, 2, 3, 3)
        try {
            val res = restTemplate.getForEntity<StaffEntity>("/staffs/1")
            Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
            val staff = res.body!!
            val request = staff.withPicture(pictureBytes).let {
                val headers = LinkedMultiValueMap<String, String>()
                headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, listOf(MediaType.APPLICATION_JSON_VALUE))
                HttpEntity(it, headers)
            }
            restTemplate.put("/staffs/${staff.id}", request)
        } catch (e: Exception) {
            Assertions.fail<Void>(e.message)
        }

        val res = restTemplate.getForEntity<StaffEntity>("/staffs/1")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val staff = res.body!!
        Assertions.assertEquals(1, staff.id)
        Assertions.assertEquals(pictureBytes.toList(), staff.picture.toList())
    }

    @Test
    fun `delete Payment(1)`() {
        try {
            restTemplate.delete("/payments/1")
        } catch (e: Exception) {
            Assertions.fail<Void>(e.message)
        }

        val res = restTemplate.getForEntity<String>("/payments/1")
        Assertions.assertEquals(HttpStatus.valueOf(res.statusCodeValue), HttpStatus.NOT_FOUND)
    }
}
