package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "city")
data class City(
    @Id
    @SequenceGenerator(name = "city_city_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    @JsonProperty("city_id")
    val id: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "city", nullable = false)
    @JsonProperty("city")
    val city: String = "",

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable = false)
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

interface CityRepository : CrudRepository<City, Int?> {
    fun findAllByOrderByCityAsc(): List<City>
}
