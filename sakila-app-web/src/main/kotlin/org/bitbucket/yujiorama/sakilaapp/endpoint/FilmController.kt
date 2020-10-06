package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Film
import org.bitbucket.yujiorama.sakilaapp.model.FilmRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class FilmController(
        @Autowired private val repository: FilmRepository
) {

    @GetMapping("/films/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Film> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/films")
    fun readAll(): List<Film> = repository.findAllByOrderByTitleAsc()

    @PostMapping("/films")
    fun create(@RequestBody aFilm: Film): Film = repository.save(aFilm)

    @PutMapping("/films/{id}")
    fun update(@RequestBody aFilm: Film, @PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            val newFilm = aFilm.copy(id = id.toInt(), lastUpdate = LocalDateTime.now())
            repository.save(newFilm)
            ResponseEntity.noContent().build<Void>()
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
