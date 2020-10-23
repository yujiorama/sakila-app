package org.bitbucket.yujiorama.sakilaapp

import org.bitbucket.yujiorama.sakilaapp.model.RatingToStringConverter
import org.bitbucket.yujiorama.sakilaapp.model.StringToRatingConverter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionManager
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories
class JdbcRepositoryConfiguration : AbstractJdbcConfiguration() {

    @ConditionalOnBean(DataSource::class)
    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations =
        NamedParameterJdbcTemplate(dataSource)

    @ConditionalOnBean(DataSource::class)
    @Bean
    fun transactionManager(dataSource: DataSource): TransactionManager =
        DataSourceTransactionManager(dataSource)

    @Bean
    override fun jdbcCustomConversions(): JdbcCustomConversions =
        JdbcCustomConversions(
            listOf(
                RatingToStringConverter(),
                StringToRatingConverter(),
            )
        )
}
