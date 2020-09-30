package org.bitbucket.yujiorama.sakilaapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@SpringBootApplication
class SakilaAppApplication

fun main(args: Array<String>) {
    runApplication<SakilaAppApplication>(*args)
}

@Configuration
@EnableJdbcRepositories
class JdbcRepositoryConfiguration : AbstractJdbcConfiguration() {

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations = NamedParameterJdbcTemplate(dataSource)
}
