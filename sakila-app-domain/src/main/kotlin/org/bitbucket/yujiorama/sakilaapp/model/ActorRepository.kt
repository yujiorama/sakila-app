package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface ActorRepository : CrudRepository<Actor, Long> {
    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Actor>
}