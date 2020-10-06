package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface StaffRepository : CrudRepository<Staff, Int> {
    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Staff>
}
