package org.bitbucket.yujiorama.sakilaapp.endpoint

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.bitbucket.yujiorama.sakilaapp.model.CountryRepository
import org.bitbucket.yujiorama.sakilaapp.model.FilmRepository
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import java.time.format.DateTimeFormatter

@ActiveProfiles("test")
@Configuration
class ControllerTestConfig {

    @Bean
    fun countryRepository(): CountryRepository = Mockito.mock(CountryRepository::class.java)

    @Bean
    fun countryController(countryRepository: CountryRepository): CountryController = CountryController(countryRepository)

    @Bean
    fun filmRepository(): FilmRepository = Mockito.mock(FilmRepository::class.java)

    @Bean
    fun filmController(filmRepository: FilmRepository): FilmController = FilmController(filmRepository)

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
