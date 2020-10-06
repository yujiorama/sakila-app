package org.bitbucket.yujiorama.sakilaapp.model

import org.springframework.data.repository.CrudRepository

interface PaymentRepository : CrudRepository<Payment, Int> {
    fun findAllByOrderByIdAsc(): List<Payment>
}
