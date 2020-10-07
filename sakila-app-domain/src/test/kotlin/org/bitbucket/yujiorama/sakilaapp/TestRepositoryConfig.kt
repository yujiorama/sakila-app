package org.bitbucket.yujiorama.sakilaapp

import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.data.relational.core.dialect.PostgresDialect
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@Configuration
@EnableJdbcRepositories
class TestRepositoryConfig : AbstractJdbcConfiguration() {
    override fun jdbcDialect(operations: NamedParameterJdbcOperations): Dialect = PostgresDialect.INSTANCE
}
