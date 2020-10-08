package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.CustomerEntity
import org.bitbucket.yujiorama.sakilaapp.model.CustomerEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CustomerController(
        @Autowired private val repository: CustomerEntityRepository
) {

    @GetMapping("/CustomerEntitys/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<CustomerEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/CustomerEntitys")
    fun readAll(): List<CustomerEntity> = repository.findAllByOrderByFirstNameAscLastNameAsc()

    @PostMapping("/CustomerEntitys")
    fun create(@RequestBody aCustomerEntity: CustomerEntity): CustomerEntity = repository.save(aCustomerEntity)

    @PutMapping("/CustomerEntitys/{id}")
    fun update(@RequestBody aCustomerEntity: CustomerEntity, @PathVariable id: Number): ResponseEntity<CustomerEntity> {

        return repository.findById(id.toInt()).map {
            val newCustomerEntity = aCustomerEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCustomerEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/CustomerEntitys/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
