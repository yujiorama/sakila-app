package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class Address(
        @Id
        @Column("address_id")
        val id: Long = -1,
        @Column("address")
        val address: String = "",
        @Column("address2")
        val address2: String? = null,
        @Column("district")
        val district: String = "",
        @MappedCollection(idColumn = "city_id")
        val city: City? = null,
        @Column("postal_code")
        val postalCode: String? = null,
        @Column("phone")
        val phone: String = "",
        @LastModifiedDate
        @Column("last_update")
        val lastUpdate: LocalDateTime? = null
)
