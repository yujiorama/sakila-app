package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "category")
data class Category(
    @Id
    @SequenceGenerator(name = "category_category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @JsonProperty("category_id")
    val id: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    val name: String = "",
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Category = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Category = this.copy(lastUpdate = lastUpdate)

    fun withName(name: String): Category = this.copy(name = name)
}

interface CategoryRepository : CrudRepository<Category, Int?> {
    fun findAllByOrderByNameAsc(): List<Category>
}
