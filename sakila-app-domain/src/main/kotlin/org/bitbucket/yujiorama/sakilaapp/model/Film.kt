package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import java.math.BigDecimal
import java.time.LocalDateTime

data class Film(
        @Id
        @Column("film_id")
        val id: Int?,
        val title: String,
        val description: String?,
        val releaseYear: Int?,
        @MappedCollection(idColumn = "language_id")
        val language: Language,
        @MappedCollection(idColumn = "language_id")
        val originalLanguage: Language?,
        val rentalDuration: Int = 3,
        val rentalRate: BigDecimal = BigDecimal("4.99"),
        val length: Int?,
        val replacementCost: BigDecimal = BigDecimal("19.99"),
        val rating: Rating? = Rating.G,
        val lastUpdate: LocalDateTime = LocalDateTime.now(),
        val specialFeatures: Array<String>,
        val fulltext: String
)
