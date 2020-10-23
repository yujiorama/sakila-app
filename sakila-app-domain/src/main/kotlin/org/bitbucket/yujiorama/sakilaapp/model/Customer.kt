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

@Table("customer")
data class Customer(
    @Id
    @Column("customer_id")
    @JsonProperty("customer_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "store_id", keyColumn = "store_id")
    @JsonProperty("store")
    val store: Store = Store(),

    @MappedCollection(idColumn = "address_id", keyColumn = "address_id")
    @JsonProperty("address")
    val address: Address = Address(),

    @Column("first_name")
    @JsonProperty("first_name")
    val firstName: String = "",

    @Column("last_name")
    @JsonProperty("last_name")
    val lastName: String = "",

    @Column("activebool")
    @JsonProperty("activebool")
    val activebool: Boolean = false,

    @Column("create_date")
    @JsonProperty("create_date")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val createDate: LocalDateTime = LocalDateTime.now(),

    @Column("email")
    @JsonProperty("email")
    val email: String?,

    @Column("active")
    @JsonProperty("active")
    val active: Int?,
) {
    @PersistenceConstructor
    constructor() : this(id = null, email = null, active = null)

    fun withId(id: Int?): Customer = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Customer = this.copy(lastUpdate = lastUpdate)

    fun withStore(store: Store): Customer = this.copy(store = store)

    fun withAddress(address: Address): Customer = this.copy(address = address)

    fun withFirstName(firstName: String): Customer = this.copy(firstName = firstName)

    fun withLastName(lastName: String): Customer = this.copy(lastName = lastName)

    fun withActivebool(activebool: Boolean): Customer = this.copy(activebool = activebool)

    fun withCreateDate(createDate: LocalDateTime): Customer = this.copy(createDate = createDate)

    fun withEmail(email: String?): Customer = this.copy(email = email)

    fun withActive(active: Int?): Customer = this.copy(active = active)
}

interface CustomerRepository : CrudRepository<Customer, Int> {
    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Customer>
}
