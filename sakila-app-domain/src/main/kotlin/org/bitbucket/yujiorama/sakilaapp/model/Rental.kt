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

@Table("rental")
data class Rental(
    @Id
    @Column("rental_id")
    @JsonProperty("rental_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "inventory_id")
    @JsonProperty("inventory")
    val inventory: Inventory = Inventory(),

    @MappedCollection(idColumn = "customer_id")
    @JsonProperty("customer")
    val customer: Customer = Customer(),

    @MappedCollection(idColumn = "staff_id")
    @JsonProperty("staff")
    val staff: Staff = Staff(),

    @Column("return_date")
    @JsonProperty("return_date")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val returnDate: LocalDateTime?,
) {
    @PersistenceConstructor
    constructor() : this(id = null, returnDate = null)

    fun withId(id: Int?): Rental = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Rental = this.copy(lastUpdate = lastUpdate)

    fun withInventory(inventory: Inventory): Rental = this.copy(inventory = inventory)

    fun withCustomer(customer: Customer): Rental = this.copy(customer = customer)

    fun withStaff(staff: Staff): Rental = this.copy(staff = staff)

    fun withReturnDate(returnDate: LocalDateTime?): Rental = this.copy(returnDate = returnDate)
}

interface RentalRepository : CrudRepository<Rental, Int> {
    fun findAllByOrderByIdAsc(): List<Rental>
}
