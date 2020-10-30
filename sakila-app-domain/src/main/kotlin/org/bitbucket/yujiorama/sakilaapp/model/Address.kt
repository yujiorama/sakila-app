package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "address")
data class Address(
    @Id
    @SequenceGenerator(name = "address_address_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @JsonProperty("address_id")
    val id: Long?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "address", nullable = false)
    @JsonProperty("address")
    val address: String = "",

    @Column(name = "district", nullable = false)
    @JsonProperty("district")
    val district: String = "",

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "city_id", referencedColumnName = "city_id", nullable = false)
    @JsonProperty("city")
    val city: City = City(),

    @Column(name = "phone", nullable = false)
    @JsonProperty("phone")
    val phone: String = "",

    @Column(name = "address2")
    @JsonProperty("address2")
    val address2: String? = null,

    @Column(name = "postal_code")
    @JsonProperty("postal_code")
    val postalCode: String? = null,
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Long?): Address = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Address = this.copy(lastUpdate = lastUpdate)

    fun withAddress(address: String): Address = this.copy(address = address)

    fun withDistrict(district: String): Address = this.copy(district = district)

    fun withCity(city: City): Address = this.copy(city = city)

    fun withPhone(phone: String): Address = this.copy(phone = phone)

    fun withAddress2(address2: String?): Address = this.copy(address2 = address2)

    fun withPostalCode(postalCode: String?): Address = this.copy(postalCode = postalCode)
}

interface AddressRepository : CrudRepository<Address, Long?> {
    fun findAllByOrderByIdAsc(): List<Address>
}
