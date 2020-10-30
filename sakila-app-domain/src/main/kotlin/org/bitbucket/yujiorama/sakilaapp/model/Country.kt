package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "country")
data class Country(
    @Id
    @SequenceGenerator(name = "country_country_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    @JsonProperty("country_id")
    val id: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "country", nullable = false)
    @JsonProperty("country")
    val country: String = "",
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Country = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Country = this.copy(lastUpdate = lastUpdate)

    fun withCountry(country: String): Country = this.copy(country = country)
}

interface CountryRepository : CrudRepository<Country, Int?> {
    fun findAllByOrderByCountryAsc(): List<Country>
}
