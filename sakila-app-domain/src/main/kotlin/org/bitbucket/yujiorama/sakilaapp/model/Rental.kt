package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class Rental(
        @Id
        @Column("rental_id")
        val id: Int?,
        val rentalDate: LocalDateTime,
        @MappedCollection(idColumn = "inventory_id")
        val inventory: Inventory,
        @MappedCollection(idColumn = "customer_id")
        val customer: Customer,
        val returnDate: LocalDateTime?,
        @MappedCollection(idColumn = "staff_id")
        val staff: Staff,
        val lastUpdate: LocalDateTime
)
