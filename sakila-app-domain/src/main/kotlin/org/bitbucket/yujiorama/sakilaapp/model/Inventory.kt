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

@Table("inventory")
data class Inventory(
    @Id
    @Column("inventory_id")
    @JsonProperty("inventory_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "film_id")
    @JsonProperty("film")
    val film: Film = Film(),

    @MappedCollection(idColumn = "store_id")
    @JsonProperty("store")
    val store: Store = Store(),
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Inventory = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Inventory = this.copy(lastUpdate = lastUpdate)

    fun withFilm(film: Film): Inventory = this.copy(film = film)

    fun withStore(store: Store): Inventory = this.copy(store = store)
}

interface InventoryRepository : CrudRepository<Inventory, Int> {
    fun findAllByOrderByIdAsc(): List<Inventory>
}