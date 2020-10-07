package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Category
import org.bitbucket.yujiorama.sakilaapp.model.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CategoryController(
        @Autowired private val repository: CategoryRepository
) {

    @GetMapping("/categories/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Category> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/categories")
    fun readAll(): List<Category> = repository.findAllByOrderByNameAsc()

    @PostMapping("/categories")
    fun create(@RequestBody aCategory: Category): Category = repository.save(aCategory)

    @PutMapping("/categories/{id}")
    fun update(@RequestBody aCategory: Category, @PathVariable id: Number): ResponseEntity<Category> {

        return repository.findById(id.toInt()).map {
            val newCategory = aCategory.copy(id = id.toInt(), lastUpdate = LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCategory))
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
