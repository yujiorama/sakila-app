package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Inventory
import org.bitbucket.yujiorama.sakilaapp.model.InventoryRepository
import org.bitbucket.yujiorama.sakilaapp.model.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class InventoryController(
    @Autowired private val repository: InventoryRepository,
    @Autowired private val storeRepository: StoreRepository,
) {

    @GetMapping("/inventories/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Inventory> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/inventories")
    fun readAll(): List<Inventory> = repository.findAllByOrderByIdAsc()

    @PostMapping("/inventories")
    fun create(@RequestBody aInventory: Inventory): Inventory = repository.save(aInventory)

    @PutMapping("/inventories/{id}")
    fun update(@RequestBody aInventory: Inventory, @PathVariable id: Number): ResponseEntity<Inventory> {

        return repository.findById(id.toInt()).map {
            val newInventoryEntity = aInventory
                .withId(id.toInt())
                .withLastUpdate(LocalDateTime.now())
                .withStore(storeRepository.findById(aInventory.store.id!!).get())
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
