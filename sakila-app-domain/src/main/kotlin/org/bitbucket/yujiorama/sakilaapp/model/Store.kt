package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "store")
data class Store(
    @Id
    @SequenceGenerator(name = "store_store_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    @JsonProperty("store_id")
    val id: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @JsonProperty("address")
    val address: Address = Address(),

    // TODO to reflect column restriction of non-null to field type nullability
    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "manager_staff_id", referencedColumnName = "staff_id", nullable = false)
    @JsonProperty("manager_staff", required = false)
    var managerStaff: Staff? = null,
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Store = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Store = this.copy(lastUpdate = lastUpdate)

    fun withAddress(address: Address): Store = this.copy(address = address)

    fun withManagerStaff(managerStaff: Staff?): Store = this.copy(managerStaff = managerStaff)

    @PrePersist
    @PreUpdate
    fun checkNullable() {
        if (managerStaff == null) {
            throw IllegalArgumentException(this.toString())
        }
    }

    override fun toString(): String {
        return "Store(id=$id, lastUpdate=$lastUpdate, address=$address, managerStaff(id)=${managerStaff?.id})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Store

        if (id != other.id) return false
        if (lastUpdate != other.lastUpdate) return false
        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + lastUpdate.hashCode()
        result = 31 * result + address.hashCode()
        return result
    }
}

interface StoreRepository : CrudRepository<Store, Int?> {
    fun findAllByOrderByIdAsc(): List<Store>
}
