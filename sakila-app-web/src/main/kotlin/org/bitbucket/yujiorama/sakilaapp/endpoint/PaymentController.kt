package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.PaymentEntity
import org.bitbucket.yujiorama.sakilaapp.model.PaymentEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class PaymentController(
        @Autowired private val repository: PaymentEntityRepository
) {

    @GetMapping("/PaymentEntitys/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<PaymentEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/PaymentEntitys")
    fun readAll(): List<PaymentEntity> = repository.findAllByOrderByIdAsc()

    @PostMapping("/PaymentEntitys")
    fun create(@RequestBody aPaymentEntity: PaymentEntity): PaymentEntity = repository.save(aPaymentEntity)

    @PutMapping("/PaymentEntitys/{id}")
    fun update(@RequestBody aPaymentEntity: PaymentEntity, @PathVariable id: Number): ResponseEntity<PaymentEntity> {

        return repository.findById(id.toInt()).map {
            val newPaymentEntity = aPaymentEntity
                    .withId(id.toInt())
                    .withPaymentDate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newPaymentEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/PaymentEntitys/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
