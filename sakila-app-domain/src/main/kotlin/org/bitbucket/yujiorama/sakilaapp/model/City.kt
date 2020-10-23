package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Table("city")
data class City(
    @Id
    @Column("city_id")
    @JsonProperty("city_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "city_id", keyColumn = "city_id")
    @JsonProperty("city")
    val city: String = "",

    @MappedCollection(idColumn = "country_id", keyColumn = "country_id")
    @JsonProperty("country")
    val country: Country = Country()
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): City = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): City = this.copy(lastUpdate = lastUpdate)

    fun withCity(city: String): City = this.copy(city = city)

    fun withCountry(country: Country): City = this.copy(country = country)
}

interface CityRepository : CrudRepository<City, Int> {
    fun findAllByOrderByCityAsc(): List<City>
}
