package org.bitbucket.yujiorama.sakilaapp.batch.payment

import org.bitbucket.yujiorama.sakilaapp.model.Payment
import org.springframework.batch.item.ItemProcessor

class PaymentProcessor : ItemProcessor<Payment, PaymentDTO> {

    override fun process(payment: Payment): PaymentDTO? {

        if (payment.id!! % 2 == 0) {
            Thread.sleep(100L)
        } else {
            Thread.sleep(200L)
        }

        return PaymentDTO.of(payment)
    }
}
