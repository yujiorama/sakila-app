package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.FilmActor
import org.bitbucket.yujiorama.sakilaapp.model.FilmActorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class FilmActorController(
        @Autowired private val repository: FilmActorRepository
) {

    @GetMapping("/filmactors/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<FilmActor> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/filmactors")
    fun readAll(): List<FilmActor> = repository.findAllByOrderByIdAsc()

    @PostMapping("/filmactors")
    fun create(@RequestBody aFilmActor: FilmActor): FilmActor = repository.save(aFilmActor)

    @PutMapping("/filmactors/{id}")
    fun update(@RequestBody aFilmActor: FilmActor, @PathVariable id: Number): ResponseEntity<FilmActor> {

        return repository.findById(id.toInt()).map {
            val newFilmActorEntity = aFilmActor
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newFilmActorEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/filmactors/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
