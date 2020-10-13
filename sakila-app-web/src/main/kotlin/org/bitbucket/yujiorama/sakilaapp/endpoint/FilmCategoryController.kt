package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.FilmCategory
import org.bitbucket.yujiorama.sakilaapp.model.FilmCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class FilmCategoryController(
        @Autowired private val repository: FilmCategoryRepository
) {

    @GetMapping("/filmcategories/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<FilmCategory> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/filmcategories")
    fun readAll(): List<FilmCategory> = repository.findAllByOrderByIdAsc()

    @PostMapping("/filmcategories")
    fun create(@RequestBody aFilmCategory: FilmCategory): FilmCategory = repository.save(aFilmCategory)

    @PutMapping("/filmcategories/{id}")
    fun update(@RequestBody aFilmCategory: FilmCategory, @PathVariable id: Number): ResponseEntity<FilmCategory> {

        return repository.findById(id.toInt()).map {
            val newFilmCategoryEntity = aFilmCategory
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newFilmCategoryEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/filmcategories/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
