package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Payment
import org.bitbucket.yujiorama.sakilaapp.model.PaymentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class PaymentController(
        @Autowired private val repository: PaymentRepository
) {

    @GetMapping("/payments/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Payment> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/payments")
    fun readAll(): List<Payment> = repository.findAllByOrderByIdAsc()

    @PostMapping("/payments")
    fun create(@RequestBody aPayment: Payment): Payment = repository.save(aPayment)

    @PutMapping("/payments/{id}")
    fun update(@RequestBody aPayment: Payment, @PathVariable id: Number): ResponseEntity<Payment> {

        return repository.findById(id.toInt()).map {
            val newPaymentEntity = aPayment
                    .withId(id.toInt())
                    .withPaymentDate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newPaymentEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/payments/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
