package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Language
import org.bitbucket.yujiorama.sakilaapp.model.LanguageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class LanguageController(
        @Autowired private val repository: LanguageRepository
) {

    @GetMapping("/languages/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Language> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/languages")
    fun readAll(): List<Language> = repository.findAllByOrderByNameAsc()

    @PostMapping("/languages")
    fun create(@RequestBody aLanguage: Language): Language = repository.save(aLanguage)

    @PutMapping("/languages/{id}")
    fun update(@RequestBody aLanguage: Language, @PathVariable id: Number): ResponseEntity<Language> {

        return repository.findById(id.toInt()).map {
            val newLanguage = aLanguage.copy(id = id.toInt(), lastUpdate = LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newLanguage))
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
