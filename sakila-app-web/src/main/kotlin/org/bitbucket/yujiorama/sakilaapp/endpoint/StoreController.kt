package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Store
import org.bitbucket.yujiorama.sakilaapp.model.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class StoreController(
        @Autowired private val repository: StoreRepository
) {

    @GetMapping("/stores/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Store> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/stores")
    fun readAll(): List<Store> = repository.findAllByOrderByIdAsc()

    @PostMapping("/stores")
    fun create(@RequestBody aStore: Store): Store = repository.save(aStore)

    @PutMapping("/stores/{id}")
    fun update(@RequestBody aStore: Store, @PathVariable id: Number): ResponseEntity<Store> {

        return repository.findById(id.toInt()).map {
            val newStoreEntity = aStore
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newStoreEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/stores/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
