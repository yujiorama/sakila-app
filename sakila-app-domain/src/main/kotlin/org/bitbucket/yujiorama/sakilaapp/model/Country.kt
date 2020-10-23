package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Table("country")
data class Country(
    @Id
    @Column("country_id")
    @JsonProperty("country_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column("country")
    @JsonProperty("country")
    val country: String = "",
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Country = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Country = this.copy(lastUpdate = lastUpdate)

    fun withCountry(country: String): Country = this.copy(country = country)
}

interface CountryRepository : CrudRepository<Country, Int> {
    fun findAllByOrderByCountryAsc(): List<Country>
}
