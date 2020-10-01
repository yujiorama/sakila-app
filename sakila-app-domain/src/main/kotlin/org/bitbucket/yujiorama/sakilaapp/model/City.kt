package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class City(
        @Id
        @Column("city_id")
        val id: Int = -1,
        @Column("city")
        val city: String = "",
        @MappedCollection(idColumn = "country_id")
        val country: Country? = null,
        @LastModifiedDate
        @Column("last_update")
        val lastUpdate: LocalDateTime? = null
)
