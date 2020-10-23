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
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("payment")
data class Payment(
    @Id
    @Column("payment_id")
    @JsonProperty("payment_id")
    val id: Int?,

    @Column("payment_date")
    @JsonProperty("payment_date")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val paymentDate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "customer_id")
    @JsonProperty("customer")
    val customer: Customer = Customer(),

    @MappedCollection(idColumn = "staff_id")
    @JsonProperty("staff")
    val staff: Staff = Staff(),

    @MappedCollection(idColumn = "rental_id")
    @JsonProperty("rental")
    val rental: Rental = Rental(),

    @Column("amount")
    @JsonProperty("amount")
    val amount: BigDecimal = BigDecimal.ZERO,
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Payment = this.copy(id = id)

    fun withPaymentDate(paymentDate: LocalDateTime): Payment = this.copy(paymentDate = paymentDate)

    fun withCustomer(customer: Customer): Payment = this.copy(customer = customer)

    fun withStaff(staff: Staff): Payment = this.copy(staff = staff)

    fun withRental(rental: Rental): Payment = this.copy(rental = rental)

    fun withAmount(amount: BigDecimal): Payment = this.copy(amount = amount)
}

interface PaymentRepository : CrudRepository<Payment, Int> {
    fun findAllByOrderByIdAsc(): List<Payment>
}
