package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class City(
        @Id
        @Column("city_id")
        val id: Int?,
        val city: String,
        @MappedCollection(idColumn = "country_id")
        val country: Country,
        val lastUpdate: LocalDateTime = LocalDateTime.now()
)
