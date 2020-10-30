package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "customer")
data class Customer(
    @Id
    @SequenceGenerator(name = "customer_customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    val id: Int?,

    @javax.persistence.Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    @JsonProperty("store")
    @JsonIgnoreProperties("manager_staff")
    val store: Store = Store(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @JsonProperty("address")
    val address: Address = Address(),

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    val firstName: String = "",

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    val lastName: String = "",

    @Column(name = "activebool", nullable = false)
    @JsonProperty("activebool")
    val activebool: Boolean = false,

    @Column(name = "create_date", nullable = false)
    @JsonProperty("create_date")
    val createDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "email")
    @JsonProperty("email")
    val email: String? = null,

    @Column(name = "active")
    @JsonProperty("active")
    val active: Int? = null,
) {
    @PersistenceConstructor
    constructor() : this(id = null)

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

interface CustomerRepository : CrudRepository<Customer, Int?> {
    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Customer>
}
