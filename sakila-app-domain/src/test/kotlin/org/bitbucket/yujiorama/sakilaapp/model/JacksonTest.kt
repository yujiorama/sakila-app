package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [JacksonTest.TestConfig::class]
)
class JacksonTest(
    @Autowired val objectMapper: ObjectMapper
) {

    @Configuration
    class TestConfig {
        @Bean
        fun objectMapper(): ObjectMapper = ObjectMapper()
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
    }

    private val logger = LoggerFactory.getLogger(JacksonTest::class.java)

    private var fixture = HashMap<String, Any>(mapOf(
        "staff" to Staff(),
        "customer" to Customer(),
    ))

    @Test
    @Throws(JsonProcessingException::class)
    fun `Staff serialization`() {
        val target = fixture["staff"]
        val serialized = objectMapper.writeValueAsString(target)
        Assertions.assertTrue(serialized.isNotEmpty(), "serialized")
        Assertions.assertNotEquals("{}", serialized)

        logger.debug(serialized)

        val deserialized = objectMapper.readValue(serialized, Staff::class.java)
        Assertions.assertEquals(target, deserialized, "deserialized")
    }

    @Test
    @Throws(JsonProcessingException::class)
    fun `Staff deserialization`() {
        val json = """
            {
              "staff_id": 1374,
              "last_update": "2020-01-02T03:04:05.678",
              "first_name": "Mike",
              "last_name": "Hillyer",
              "address": {
                "address_id": 1374,
                "last_update": "2020-01-02T03:04:05.678",
                "address": "ころころ",
                "district": "ひそひそ",
                "city": {
                  "city_id": 1374,
                  "last_update": "2020-01-02T03:04:05.678",
                  "city": "Lethbridge",
                  "country": {
                    "country_id": 1374,
                    "last_update": "2020-01-02T03:04:05.678",
                    "country": "Canada"
                  }
                },
                "phone": "0123-45-6789",
                "address2": null,
                "postal_code": "012-3456"
              },
              "email": "aaa@example.com",
              "store": {
                "store_id": 1374,
                "last_update": "2020-01-02T03:04:05.678",
                "address": {
                  "address_id": 1374,
                  "last_update": "2020-01-02T03:04:05.678",
                  "address": "ころころ",
                  "district": "ひそひそ",
                  "city": {
                    "city_id": 1374,
                    "last_update": "2020-01-02T03:04:05.678",
                    "city": "Lethbridge",
                    "country": {
                      "country_id": 1374,
                      "last_update": "2020-01-02T03:04:05.678",
                      "country": "Canada"
                    }
                  },
                  "phone": "0123-45-6789",
                  "address2": null,
                  "postal_code":  "012-3456"
                }
              },
              "active": true,
              "username": "Mike",
              "password": "8cb2237d0679ca88db6464eac60da96345513964",
              "picture": null
            }
        """.trimIndent()
        val expected = fixture["staff"]
        val actual = objectMapper.readValue(json, Staff::class.java)
        Assertions.assertEquals(expected, actual)
    }

    @Test
    @Throws(JsonProcessingException::class)
    fun `Customer serialization`() {
        var target = fixture["customer"]
        var serialized = objectMapper.writeValueAsString(target)
        Assertions.assertTrue(serialized.isNotEmpty(), "serialized")
        Assertions.assertNotEquals("{}", serialized)

        logger.debug(serialized)

        val deserialized = objectMapper.readValue(serialized, Customer::class.java)
        Assertions.assertEquals(target, deserialized, "deserialized")
    }

    @Test
    @Throws(JsonProcessingException::class)
    fun `Customer deserialization`() {
        val json = """
            {
              "customer_id": 1374,
              "last_update": "2020-01-02T03:04:05.678",
              "store": {
                "store_id": 1374,
                "last_update": "2020-01-02T03:04:05.678",
                "address": {
                  "address_id": 1374,
                  "last_update": "2020-01-02T03:04:05.678",
                  "address": "ころころ",
                  "district": "ひそひそ",
                  "city": {
                    "city_id": 1374,
                    "last_update": "2020-01-02T03:04:05.678",
                    "city": "Lethbridge",
                    "country": {
                      "country_id": 1374,
                      "last_update": "2020-01-02T03:04:05.678",
                      "country": "Canada"
                    }
                  },
                  "phone": "0123-45-6789",
                  "address2": null,
                  "postal_code":  "012-3456"
                }
              },
              "address": {
                "address_id": 1374,
                "last_update": "2020-01-02T03:04:05.678",
                "address": "ころころ",
                "district": "ひそひそ",
                "city": {
                  "city_id": 1374,
                  "last_update": "2020-01-02T03:04:05.678",
                  "city": "Lethbridge",
                  "country": {
                    "country_id": 1374,
                    "last_update": "2020-01-02T03:04:05.678",
                    "country": "Canada"
                  }
                },
                "phone": "0123-45-6789",
                "address2": null,
                "postal_code": "012-3456"
              },
              "first_name": "LINDA",
              "last_name": "WILLIAMS",
              "activebool": true,
              "create_date": "2020-01-02T03:04:05.678",
              "email": "aaa@example.net",
              "active": 1
            }
        """.trimIndent()
        val expected = fixture["customer"] as Customer
        val actual = objectMapper.readValue(json, Customer::class.java)
        Assertions.assertEquals(expected, actual)
    }

    @BeforeEach
    fun fixture() {
        val timestamp = LocalDateTime.of(2020, 1, 2, 3, 4, 5, 678_000_000)
        val country = Country()
            .withId(1374)
            .withCountry("Canada")
            .withLastUpdate(timestamp)
        val city = City()
            .withId(1374)
            .withCity("Lethbridge")
            .withCountry(country)
            .withLastUpdate(timestamp)
        val address = Address()
            .withId(1374)
            .withAddress("ころころ")
            .withDistrict("ひそひそ")
            .withCity(city)
            .withPhone("0123-45-6789")
            .withPostalCode("012-3456")
            .withLastUpdate(timestamp)
        var store = Store()
            .withId(1374)
            .withAddress(address)
            .withLastUpdate(timestamp)
        val staff = Staff()
            .withId(1374)
            .withFirstName("Mike")
            .withLastName("Hillyer")
            .withAddress(address)
            .withEmail("aaa@example.com")
            .withActive(true)
            .withUsername("Mike")
            .withPassword("8cb2237d0679ca88db6464eac60da96345513964")
            .withStore(store)
            .withLastUpdate(timestamp)
        store = store.withManagerStaff(staff)

        val customer = Customer()
            .withId(1374)
            .withFirstName("LINDA")
            .withLastName("WILLIAMS")
            .withActivebool(true)
            .withCreateDate(timestamp)
            .withEmail("aaa@example.net")
            .withActive(1)
            .withLastUpdate(timestamp)
            .withStore(store)
            .withAddress(address)

        fixture["staff"] = staff
        fixture["customer"] = customer
    }
}
