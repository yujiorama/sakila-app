package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Int> {
    fun findAllByOrderByNameAsc(): List<Category>
}
