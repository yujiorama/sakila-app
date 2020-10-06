package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class Inventory(
        @Id
        @Column("inventory_id")
        val id: Int?,
        @MappedCollection(idColumn = "film_id")
        val film: Film,
        @MappedCollection(idColumn = "store_id")
        val store: Store,
        val lastUpdate: LocalDateTime = LocalDateTime.now()
)
