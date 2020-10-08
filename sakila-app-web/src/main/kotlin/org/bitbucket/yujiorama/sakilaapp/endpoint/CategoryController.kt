package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.CategoryEntity
import org.bitbucket.yujiorama.sakilaapp.model.CategoryEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CategoryController(
        @Autowired private val repository: CategoryEntityRepository
) {

    @GetMapping("/categories/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<CategoryEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/categories")
    fun readAll(): List<CategoryEntity> = repository.findAllByOrderByNameAsc()

    @PostMapping("/categories")
    fun create(@RequestBody aCategoryEntity: CategoryEntity): CategoryEntity = repository.save(aCategoryEntity)

    @PutMapping("/categories/{id}")
    fun update(@RequestBody aCategoryEntity: CategoryEntity, @PathVariable id: Number): ResponseEntity<CategoryEntity> {

        return repository.findById(id.toInt()).map {
            val newCategoryEntity = aCategoryEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCategoryEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/categories/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
