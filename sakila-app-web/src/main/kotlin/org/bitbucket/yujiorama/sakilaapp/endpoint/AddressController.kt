package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Address
import org.bitbucket.yujiorama.sakilaapp.model.AddressRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class AddressController(
        @Autowired private val repository: AddressRepository
) {

    @GetMapping("/addresses/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Address> {

        return repository.findById(id.toLong()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/addresses")
    fun readAll(): List<Address> = repository.findAllByOrderByIdAsc()

    @PostMapping("/addresses")
    fun create(@RequestBody aAddress: Address): Address = repository.save(aAddress)

    @PutMapping("/addresses/{id}")
    fun update(@RequestBody aAddress: Address, @PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toLong()).map {
            val newAddress = aAddress.copy(id = id.toLong(), lastUpdate = LocalDateTime.now())
            repository.save(newAddress)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/addresses/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toLong()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
