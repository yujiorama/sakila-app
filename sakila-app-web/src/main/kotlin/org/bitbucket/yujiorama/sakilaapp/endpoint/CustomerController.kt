package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Customer
import org.bitbucket.yujiorama.sakilaapp.model.CustomerRepository
import org.bitbucket.yujiorama.sakilaapp.model.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CustomerController(
    @Autowired private val repository: CustomerRepository,
    @Autowired private val storeRepository: StoreRepository,
) {

    @GetMapping("/customers/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Customer> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/customers")
    fun readAll(): List<Customer> = repository.findAllByOrderByFirstNameAscLastNameAsc()

    @PostMapping("/customers")
    fun create(@RequestBody aCustomer: Customer): Customer = repository.save(aCustomer)

    @PutMapping("/customers/{id}")
    fun update(@RequestBody aCustomer: Customer, @PathVariable id: Number): ResponseEntity<Customer> {

        return repository.findById(id.toInt()).map {
            val newCustomerEntity = aCustomer
                .withId(id.toInt())
                .withLastUpdate(LocalDateTime.now())
                .withStore(storeRepository.findById(aCustomer.store.id!!).get())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCustomerEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/customers/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
