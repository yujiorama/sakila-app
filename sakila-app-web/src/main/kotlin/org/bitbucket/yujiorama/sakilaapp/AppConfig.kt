package org.bitbucket.yujiorama.sakilaapp

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter

@Configuration
class AppConfig {
    @Bean
    fun jacksonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {

        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            run {
                builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
                builder.deserializers(LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
            }
        }
    }
}