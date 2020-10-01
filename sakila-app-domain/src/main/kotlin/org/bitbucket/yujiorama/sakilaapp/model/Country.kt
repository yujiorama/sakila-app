package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Country(
        @Id
        @Column("country_id")
        val id: Int = -1,
        @Column("country")
        val country: String = "",
        @LastModifiedDate
        @Column("last_update")
        val lastUpdate: LocalDateTime? = null
)
