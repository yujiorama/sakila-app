package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.AddressEntity
import org.bitbucket.yujiorama.sakilaapp.model.AddressEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class AddressController(
        @Autowired private val repository: AddressEntityRepository
) {

    @GetMapping("/addresses/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<AddressEntity> {

        return repository.findById(id.toLong()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/addresses")
    fun readAll(): List<AddressEntity> = repository.findAllByOrderByIdAsc()

    @PostMapping("/addresses")
    fun create(@RequestBody aAddress: AddressEntity): AddressEntity = repository.save(aAddress)

    @PutMapping("/addresses/{id}")
    fun update(@RequestBody aAddress: AddressEntity, @PathVariable id: Number): ResponseEntity<AddressEntity> {

        return repository.findById(id.toLong()).map {
            val newAddress = aAddress
                    .withId(id.toLong())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newAddress))
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
