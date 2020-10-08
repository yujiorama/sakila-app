package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.InventoryEntity
import org.bitbucket.yujiorama.sakilaapp.model.InventoryEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class InventoryController(
        @Autowired private val repository: InventoryEntityRepository
) {

    @GetMapping("/inventories/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<InventoryEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/inventories")
    fun readAll(): List<InventoryEntity> = repository.findAllByOrderByIdAsc()

    @PostMapping("/inventories")
    fun create(@RequestBody aInventoryEntity: InventoryEntity): InventoryEntity = repository.save(aInventoryEntity)

    @PutMapping("/inventories/{id}")
    fun update(@RequestBody aInventoryEntity: InventoryEntity, @PathVariable id: Number): ResponseEntity<InventoryEntity> {

        return repository.findById(id.toInt()).map {
            val newInventoryEntity = aInventoryEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newInventoryEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/inventories/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
