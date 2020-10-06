package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface FilmRepository : CrudRepository<Film, Int> {
    fun findAllByOrderByTitleAsc(): List<Film>
}
