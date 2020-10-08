package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.StoreEntity
import org.bitbucket.yujiorama.sakilaapp.model.StoreEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class StoreController(
        @Autowired private val repository: StoreEntityRepository
) {

    @GetMapping("/StoreEntitys/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<StoreEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/StoreEntitys")
    fun readAll(): List<StoreEntity> = repository.findAllByOrderByIdAsc()

    @PostMapping("/StoreEntitys")
    fun create(@RequestBody aStoreEntity: StoreEntity): StoreEntity = repository.save(aStoreEntity)

    @PutMapping("/StoreEntitys/{id}")
    fun update(@RequestBody aStoreEntity: StoreEntity, @PathVariable id: Number): ResponseEntity<StoreEntity> {

        return repository.findById(id.toInt()).map {
            val newStoreEntity = aStoreEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newStoreEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/StoreEntitys/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
