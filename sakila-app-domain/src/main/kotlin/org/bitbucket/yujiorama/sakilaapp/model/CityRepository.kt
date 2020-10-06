package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface CityRepository : CrudRepository<City, Int> {
    fun findAllByOrderByCityAsc(): List<City>
}
