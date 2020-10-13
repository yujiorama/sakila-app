package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Actor
import org.bitbucket.yujiorama.sakilaapp.model.ActorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class ActorController(
        @Autowired private val repository: ActorRepository
) {

    @GetMapping("/actors/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Actor> {

        return repository.findById(id.toLong()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/actors")
    fun readAll(): List<Actor> = repository.findAllByOrderByFirstNameAscLastNameAsc()

    @PostMapping("/actors")
    fun create(@RequestBody aActor: Actor): Actor = repository.save(aActor)

    @PutMapping("/actors/{id}")
    fun update(@RequestBody aActor: Actor, @PathVariable id: Number): ResponseEntity<Actor> {

        return repository.findById(id.toLong()).map {
            val newActorEntity = aActor
                    .withId(id.toLong())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newActorEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/actors/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toLong()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
