package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "rental")
data class Rental(
    @Id
    @SequenceGenerator(name = "rental_rental_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    @JsonProperty("rental_id")
    val id: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id", nullable = false)
    @JsonProperty("inventory")
    val inventory: Inventory = Inventory(),

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

    @Column(name = "return_date", nullable = false)
    @JsonProperty("return_date")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val returnDate: LocalDateTime = LocalDateTime.now(),

    ) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Rental = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Rental = this.copy(lastUpdate = lastUpdate)

    fun withInventory(inventory: Inventory): Rental = this.copy(inventory = inventory)

    fun withCustomer(customer: Customer): Rental = this.copy(customer = customer)

    fun withStaff(staff: Staff): Rental = this.copy(staff = staff)

    fun withReturnDate(returnDate: LocalDateTime): Rental = this.copy(returnDate = returnDate)
}

interface RentalRepository : CrudRepository<Rental, Int?> {
    fun findAllByOrderByIdAsc(): List<Rental>
}
