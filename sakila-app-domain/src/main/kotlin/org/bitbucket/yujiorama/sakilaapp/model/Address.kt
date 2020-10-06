package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class Address(
        @Id
        @Column("address_id")
        val id: Long?,
        val address: String,
        val address2: String?,
        val district: String,
        @MappedCollection(idColumn = "city_id")
        val city: City,
        val postalCode: String?,
        val phone: String,
        val lastUpdate: LocalDateTime = LocalDateTime.now()
) {
        constructor(address: String, district: String, city: City, phone: String) : this(null, address, null, district, city, null, phone)
}
