package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Actor(
        @Id
        @Column("actor_id")
        var actorId: Long = -1,
        @Column("first_name")
        var firstName: String = "",
        @Column("last_name")
        var lastName: String = "",
        @LastModifiedDate
        @Column("last_update")
        var lastUpdate: LocalDateTime? = null
)
