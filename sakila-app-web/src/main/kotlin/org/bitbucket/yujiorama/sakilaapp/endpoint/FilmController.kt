package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.FilmEntity
import org.bitbucket.yujiorama.sakilaapp.model.FilmEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class FilmController(
        @Autowired private val repository: FilmEntityRepository
) {

    @GetMapping("/films/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<FilmEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/films")
    fun readAll(): List<FilmEntity> = repository.findAllByOrderByTitleAsc()

    @PostMapping("/films")
    fun create(@RequestBody aFilmEntity: FilmEntity): FilmEntity = repository.save(aFilmEntity)

    @PutMapping("/films/{id}")
    fun update(@RequestBody aFilmEntity: FilmEntity, @PathVariable id: Number): ResponseEntity<FilmEntity> {

        return repository.findById(id.toInt()).map {
            val newFilmEntity = aFilmEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newFilmEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/films/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
