package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface AddressRepository : CrudRepository<Address, Long> {
    fun findAllByOrderByIdAsc(): List<Address>
}
