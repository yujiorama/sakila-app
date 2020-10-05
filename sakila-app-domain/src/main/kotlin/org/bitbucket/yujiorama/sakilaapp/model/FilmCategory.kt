package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

data class FilmCategory(
        @Id
        @Column("film_id")
        val id: Int?,
        @MappedCollection(idColumn = "category_id")
        val category: Category,
        val lastUpdate: LocalDateTime
)
