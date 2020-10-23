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

@Table("address")
data class Address(
    @Id
    @Column("address_id")
    @JsonProperty("address_id")
    val id: Long?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column("address")
    @JsonProperty("address")
    val address: String = "",

    @Column("district")
    @JsonProperty("district")
    val district: String = "",

    @MappedCollection(idColumn = "city_id", keyColumn = "city_id")
    @JsonProperty("city")
    val city: City = City(),

    @Column("phone")
    @JsonProperty("phone")
    val phone: String = "",

    @Column("address2")
    @JsonProperty("address2")
    val address2: String?,

    @Column("postal_code")
    @JsonProperty("postal_code")
    val postalCode: String?
) {
    @PersistenceConstructor
    constructor() : this(id = null, address2 = null, postalCode = null)

    fun withId(id: Long?): Address = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Address = this.copy(lastUpdate = lastUpdate)

    fun withAddress(address: String): Address = this.copy(address = address)

    fun withDistrict(district: String): Address = this.copy(district = district)

    fun withCity(city: City): Address = this.copy(city = city)

    fun withPhone(phone: String): Address = this.copy(phone = phone)

    fun withAddress2(address2: String?): Address = this.copy(address2 = address2)

    fun withPostalCode(postalCode: String?): Address = this.copy(postalCode = postalCode)
}

interface AddressRepository : CrudRepository<Address, Long> {
    fun findAllByOrderByIdAsc(): List<Address>
}
