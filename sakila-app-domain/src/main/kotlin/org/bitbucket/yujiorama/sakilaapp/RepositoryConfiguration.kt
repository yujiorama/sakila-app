package org.bitbucket.yujiorama.sakilaapp

import org.bitbucket.yujiorama.sakilaapp.impl.PGobjectToStringConverter
import org.bitbucket.yujiorama.sakilaapp.impl.StringToRatingConverter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories
@EnableAutoConfiguration
class RepositoryConfiguration : AbstractJdbcConfiguration() {

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations = NamedParameterJdbcTemplate(dataSource)

    override fun jdbcCustomConversions(): JdbcCustomConversions = JdbcCustomConversions(listOf(
            PGobjectToStringConverter(),
            StringToRatingConverter()
    ))
}
