package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.math.BigDecimal
import java.time.LocalDateTime

data class Payment(
        @Id
        @Column("payment_id")
        val id: Int?,
        @MappedCollection(idColumn = "customer_id")
        val customer: Customer,
        @MappedCollection(idColumn = "staff_id")
        val staff: Staff,
        @MappedCollection(idColumn = "rental_id")
        val rental: Rental,
        val amount: BigDecimal,
        val paymentDate: LocalDateTime = LocalDateTime.now()
)
