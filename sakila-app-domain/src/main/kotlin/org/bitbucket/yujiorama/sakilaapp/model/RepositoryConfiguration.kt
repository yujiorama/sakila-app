package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@Configuration
@EnableJdbcRepositories
class RepositoryConfiguration : AbstractJdbcConfiguration()
