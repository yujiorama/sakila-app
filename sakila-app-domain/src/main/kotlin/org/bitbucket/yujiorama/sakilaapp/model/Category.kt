package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Category(
        @Id
        @Column("category_id")
        val id: Int?,
        val name: String,
        val lastUpdate: LocalDateTime = LocalDateTime.now()
)
