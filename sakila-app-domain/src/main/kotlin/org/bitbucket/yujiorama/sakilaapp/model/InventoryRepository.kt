package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface InventoryRepository : CrudRepository<Inventory, Int> {
    fun findAllByOrderByIdAsc(): List<Inventory>
}
