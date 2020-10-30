package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @SequenceGenerator(name = "payment_payment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    @JsonProperty("payment_id")
    val id: Int?,

    @Column(name = "payment_date", nullable = false)
    @JsonProperty("payment_date")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val paymentDate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @JsonProperty("customer")
    val customer: Customer = Customer(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id", nullable = false)
    @JsonProperty("staff")
    val staff: Staff = Staff(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id", nullable = false)
    @JsonProperty("rental")
    val rental: Rental = Rental(),

    @Column(name = "amount", nullable = false)
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

interface PaymentRepository : CrudRepository<Payment, Int?> {
    fun findAllByOrderByIdAsc(): List<Payment>
}
