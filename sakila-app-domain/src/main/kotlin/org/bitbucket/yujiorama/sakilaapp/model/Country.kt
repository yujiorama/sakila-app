package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Country(
        @Id
        @Column("country_id")
        val id: Int?,
        val country: String,
        val lastUpdate: LocalDateTime
) {
    constructor(id: Int?, country: String) : this(id, country, LocalDateTime.now())
    constructor(country: String, lastUpdate: LocalDateTime) : this(null, country, lastUpdate)
}
