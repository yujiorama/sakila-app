package org.bitbucket.yujiorama.sakilaapp

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainerProvider

class TestDatabaseInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        database.start()
        TestPropertyValues.of(
            "spring.datasource.url=${database.jdbcUrl}",
            "spring.datasource.username=${database.username}",
            "spring.datasource.password=${database.password}",
            "spring.flyway.placeholders.DB_USER=${database.username}",
            "spring.flyway.placeholders.DB_PASSWORD=${database.password}"
        ).applyTo(applicationContext.environment)
    }

    companion object {
        val database = PostgreSQLContainerProvider()
            .newInstance("13-alpine")
            .withDatabaseName("sakila")
            .withUsername("postgres")
            .withPassword("postgres")
    }
}
