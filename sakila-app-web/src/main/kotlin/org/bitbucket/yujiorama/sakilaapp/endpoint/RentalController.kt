package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.RentalEntity
import org.bitbucket.yujiorama.sakilaapp.model.RentalEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class RentalController(
        @Autowired private val repository: RentalEntityRepository
) {

    @GetMapping("/RentalEntitys/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<RentalEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/RentalEntitys")
    fun readAll(): List<RentalEntity> = repository.findAllByOrderByIdAsc()

    @PostMapping("/RentalEntitys")
    fun create(@RequestBody aRentalEntity: RentalEntity): RentalEntity = repository.save(aRentalEntity)

    @PutMapping("/RentalEntitys/{id}")
    fun update(@RequestBody aRentalEntity: RentalEntity, @PathVariable id: Number): ResponseEntity<RentalEntity> {

        return repository.findById(id.toInt()).map {
            val newRentalEntity = aRentalEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newRentalEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/RentalEntitys/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
