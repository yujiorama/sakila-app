package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.FilmCategory
import org.bitbucket.yujiorama.sakilaapp.model.FilmCategoryId
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

    @GetMapping("/filmcategories/{filmId}/{categoryId}")
    fun read(@PathVariable filmId: Number, @PathVariable categoryId: Number): ResponseEntity<FilmCategory> {

        return repository.findById(FilmCategoryId(filmId.toInt(), categoryId.toInt())).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/filmcategories")
    fun readAll(): List<FilmCategory> = repository.findAllByOrderByFilmIdAscCategoryAsc()

    @PostMapping("/filmcategories")
    fun create(@RequestBody aFilmCategory: FilmCategory): FilmCategory = repository.save(aFilmCategory)

    @PutMapping("/filmcategories/{filmId}/{categoryId}")
    fun update(
        @RequestBody aFilmCategory: FilmCategory,
        @PathVariable filmId: Number,
        @PathVariable categoryId: Number): ResponseEntity<FilmCategory> {

        return repository.findById(FilmCategoryId(filmId.toInt(), categoryId.toInt())).map {
            val newFilmCategoryEntity = aFilmCategory
                .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newFilmCategoryEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/filmcategories/{filmId}/{categoryId}")
    fun delete(@PathVariable filmId: Number, @PathVariable categoryId: Number): ResponseEntity<Void> {

        return repository.findById(FilmCategoryId(filmId.toInt(), categoryId.toInt())).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
