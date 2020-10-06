package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface StoreRepository : CrudRepository<Store, Int> {
    fun findAllByOrderByIdAsc(): List<Store>
}
