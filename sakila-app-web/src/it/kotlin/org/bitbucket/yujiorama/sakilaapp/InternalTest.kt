package org.bitbucket.yujiorama.sakilaapp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.bitbucket.yujiorama.sakilaapp.model.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
    initializers = [TestDatabaseInitializer::class],
)
class InternalTest(
    @Autowired private val restTemplate: TestRestTemplate,
) {

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(
            SimpleModule()
                .addSerializer(
                    LocalDateTime::class.java,
                    LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                ).addDeserializer(
                    LocalDateTime::class.java,
                    LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
        )

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
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
    }

    @Test
    fun `read Film(PG)`() {
        val res = restTemplate.getForEntity<Film>("/films/1")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val film = res.body!!
        Assertions.assertEquals(1, film.id)
        Assertions.assertEquals("ACADEMY DINOSAUR", film.title)
        Assertions.assertEquals(Rating.PG, film.rating)
    }

    @Test
    fun `read Film(PG-13)`() {
        val res = restTemplate.getForEntity<Film>("/films/7")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val film = res.body!!
        Assertions.assertEquals(7, film.id)
        Assertions.assertEquals("AIRPLANE SIERRA", film.title)
        Assertions.assertEquals(Rating.PG_13, film.rating)
    }

    @Test
    fun `read Country(United States)`() {
        val res = restTemplate.getForEntity<Country>("/countries/103")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val country = res.body
        Assertions.assertEquals(103, country?.id)
        Assertions.assertEquals("United States", country?.country)
    }

    @Test
    fun `read Actor(Judy Dean)`() {
        val res = restTemplate.getForEntity<Actor>("/actors/35")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val actor = res.body
        Assertions.assertEquals(35, actor?.id)
        Assertions.assertEquals("judy", actor?.firstName?.toLowerCase())
        Assertions.assertEquals("dean", actor?.lastName?.toLowerCase())
    }

    @Test
    fun `create Language(Spanish)`() {
        val res = restTemplate.postForEntity<Language>("/languages", Language().withName("Spanish"), Language::class.java)
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val language = res.body
        Assertions.assertNotNull(language?.id)
        Assertions.assertEquals("spanish", language?.name?.toLowerCase())
    }

    @Test
    fun `update Category(Sci-Fi to SF)`() {
        try {
            val res = restTemplate.getForEntity<Category>("/categories/14")
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

        val res = restTemplate.getForEntity<Category>("/categories/14")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val newCategory = res.body
        Assertions.assertEquals(14, newCategory?.id)
        Assertions.assertEquals("SF", newCategory?.name)
    }

    @Test
    fun `update Customer(first,last)`() {
        try {
            val res = restTemplate.getForEntity<Customer>("/customers/3")
            Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
            val customer = res.body!!
            val request = customer.withFirstName("first").withLastName("last").let {
                val headers = LinkedMultiValueMap<String, String>()
                headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, listOf(MediaType.APPLICATION_JSON_VALUE))
                HttpEntity(it, headers)
            }
            restTemplate.put("/customers/${customer.id}", request)
        } catch (e: Exception) {
            Assertions.fail<Void>(e.message)
        }

        val res = restTemplate.getForEntity<Customer>("/customers/3")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val newCustomer = res.body!!
        Assertions.assertEquals(3, newCustomer.id)
        Assertions.assertEquals("first", newCustomer.firstName)
        Assertions.assertEquals("last", newCustomer.lastName)
    }

    private val logger = LoggerFactory.getLogger(InternalTest::class.java)

    @Test
    fun `read and modify Staff picture`() {
        val pictureBytes = byteArrayOf(1, 1, 2, 2, 3, 3)
        try {
            // TODO need appropriate configuration for TestRestTemplate
            val res = restTemplate.getForEntity<String>("/staffs/1")
            Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
            logger.debug(res.body)
            val staff = objectMapper.readValue(res.body, Staff::class.java)
            val request = staff.withPicture(pictureBytes).let {
                val headers = LinkedMultiValueMap<String, String>()
                headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, listOf(MediaType.APPLICATION_JSON_VALUE))
                HttpEntity(it, headers)
            }
            restTemplate.put("/staffs/${staff.id}", request)
        } catch (e: Exception) {
            Assertions.fail<Void>(e.message)
        }

        val res = restTemplate.getForEntity<Staff>("/staffs/1")
        Assertions.assertTrue(res.statusCode.is2xxSuccessful, "statusCode=[${res.statusCode}]")
        val staff = res.body!!
        Assertions.assertEquals(1, staff.id)
        Assertions.assertEquals(pictureBytes.toList(), staff.picture?.toList())
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
