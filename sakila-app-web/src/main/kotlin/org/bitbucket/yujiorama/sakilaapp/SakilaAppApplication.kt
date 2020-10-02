package org.bitbucket.yujiorama.sakilaapp

import org.bitbucket.yujiorama.sakilaapp.model.RepositoryConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@SpringBootApplication
@Import(RepositoryConfiguration::class)
class SakilaAppApplication

fun main(args: Array<String>) {
    runApplication<SakilaAppApplication>(*args)
}

@Bean
fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations = NamedParameterJdbcTemplate(dataSource)
