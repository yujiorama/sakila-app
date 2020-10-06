package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Int> {
    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Customer>
}
