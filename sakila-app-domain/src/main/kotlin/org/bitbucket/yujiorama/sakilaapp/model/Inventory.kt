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
@Table(name = "inventory")
data class Inventory(
    @Id
    @SequenceGenerator(name = "inventory_inventory_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    @JsonProperty("inventory_id")
    val id: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    @JsonProperty("film")
    val film: Film = Film(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    @JsonProperty("store")
    @JsonIgnoreProperties("manager_staff")
    val store: Store = Store(),
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Inventory = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Inventory = this.copy(lastUpdate = lastUpdate)

    fun withFilm(film: Film): Inventory = this.copy(film = film)

    fun withStore(store: Store): Inventory = this.copy(store = store)
}

interface InventoryRepository : CrudRepository<Inventory, Int?> {
    fun findAllByOrderByIdAsc(): List<Inventory>
}
