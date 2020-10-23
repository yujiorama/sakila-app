package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@ContextConfiguration(
    classes = [ActorTest.TestConfig::class]
)
class ActorTest(
    @Autowired val objectMapper: ObjectMapper
) {

    private val LF = "\n"

    @Configuration
    class TestConfig {
        @Bean
        fun objectMapper(): ObjectMapper = ObjectMapper()
            .registerKotlinModule()
            .registerModule(
                SimpleModule("localDateTimeModule")
                    .addSerializer(
                        LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    )
                    .addDeserializer(
                        LocalDateTime::class.java,
                        LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    )
            )
    }

    @Test
    fun `serialize`() {

        val actor = Actor()
            .withId(1)
            .withFirstName("aaa")
            .withLastName("bbb")
            .withLastUpdate(LocalDateTime
                .of(2020, 1, 2, 3, 4, 5, 678_000_000))
        val serialized = objectMapper.writeValueAsString(actor)
        Assertions.assertTrue(serialized.isNotEmpty(), "serialized")
        val expected = """
            {
              "actor_id":1,
              "last_update":"2020-01-02T03:04:05.678",
              "first_name":"aaa",
              "last_name":"bbb"
            }
        """.split(LF).joinToString("") { it.trim() }
        Assertions.assertEquals(expected, serialized)
    }

    @Test
    fun `deserialize`() {

        val serialized = """
            {
                "actor_id": 123,
                "last_update": "2020-01-02T03:04:05.678",
                "first_name": "first",
                "last_name": "last"
            }
        """.trimIndent()

        val deserialized = objectMapper.readValue(serialized, Actor::class.java)
        Assertions.assertNotNull(deserialized, "deserialized")
        val expected = Actor()
            .withId(123)
            .withFirstName("first")
            .withLastName("last")
            .withLastUpdate(LocalDateTime
                .of(2020, 1, 2, 3, 4, 5, 678_000_000))
        Assertions.assertEquals(expected, deserialized)
    }
}