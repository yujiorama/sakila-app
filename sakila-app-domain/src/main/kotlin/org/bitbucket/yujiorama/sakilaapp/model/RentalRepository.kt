package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface RentalRepository : CrudRepository<Rental, Int> {
    fun findAllByOrderByIdAsc(): List<Rental>
}
