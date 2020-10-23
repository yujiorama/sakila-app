package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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

@Table("store")
data class Store(
    @Id
    @Column("store_id")
    @JsonProperty("store_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "address_id")
    @JsonProperty("address")
    val address: Address = Address(),

    @MappedCollection(idColumn = "staff_id")
    @JsonProperty("manager_staff")
    @JsonIgnoreProperties("manager_staff")
    val managerStaff: Staff?,
) {
    @PersistenceConstructor
    constructor() : this(id = null, managerStaff = null)

    fun withId(id: Int?): Store = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Store = this.copy(lastUpdate = lastUpdate)

    fun withAddress(address: Address): Store = this.copy(address = address)

    fun withManagerStaff(managerStaff: Staff?): Store = this.copy(managerStaff = managerStaff!!.withStore(this))
}

interface StoreRepository : CrudRepository<Store, Int> {
    fun findAllByOrderByIdAsc(): List<Store>
}
