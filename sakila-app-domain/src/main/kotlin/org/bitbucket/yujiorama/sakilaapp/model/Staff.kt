package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class Staff(
        @Id
        @Column("staff_id")
        val id: Int?,
        val firstName: String,
        val lastName: String,
        @MappedCollection(idColumn = "address_id")
        val address: Address,
        val email: String?,
        @MappedCollection(idColumn = "store_id")
        val store: Store,
        val active: Boolean = true,
        @Column("username")
        val userName: String,
        val password: String?,
        val lastUpdate: LocalDateTime = LocalDateTime.now(),
        val picture: ByteArray?
)
