package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Actor(
        @Id
        @Column("actor_id")
        val id: Long?,
        val firstName: String,
        val lastName: String,
        val lastUpdate: LocalDateTime = LocalDateTime.now()
)
