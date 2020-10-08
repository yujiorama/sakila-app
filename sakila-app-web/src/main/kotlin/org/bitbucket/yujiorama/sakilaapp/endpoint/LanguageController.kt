package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.LanguageEntity
import org.bitbucket.yujiorama.sakilaapp.model.LanguageEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class LanguageController(
        @Autowired private val repository: LanguageEntityRepository
) {

    @GetMapping("/languages/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<LanguageEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/languages")
    fun readAll(): List<LanguageEntity> = repository.findAllByOrderByNameAsc()

    @PostMapping("/languages")
    fun create(@RequestBody aLanguageEntity: LanguageEntity): LanguageEntity = repository.save(aLanguageEntity)

    @PutMapping("/languages/{id}")
    fun update(@RequestBody aLanguageEntity: LanguageEntity, @PathVariable id: Number): ResponseEntity<LanguageEntity> {

        return repository.findById(id.toInt()).map {
            val newLanguageEntity = aLanguageEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newLanguageEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/languages/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
