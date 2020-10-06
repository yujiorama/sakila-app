package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class Customer(
        @Id
        @Column("customer_id")
        val id: Int?,
        @MappedCollection(idColumn = "store_id")
        val store: Store,
        val firstName: String,
        val lastName: String,
        val email: String?,
        @MappedCollection(idColumn = "address_id")
        val address: Address,
        val activebool: Boolean = true,
        val createDate: LocalDateTime,
        val lastUpdate: LocalDateTime = LocalDateTime.now(),
        val active: Int?
)
