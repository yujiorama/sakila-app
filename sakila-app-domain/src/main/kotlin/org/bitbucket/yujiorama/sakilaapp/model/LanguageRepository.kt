package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface LanguageRepository : CrudRepository<Language, Int> {
    fun findAllByOrderByNameAsc(): List<Language>
}
