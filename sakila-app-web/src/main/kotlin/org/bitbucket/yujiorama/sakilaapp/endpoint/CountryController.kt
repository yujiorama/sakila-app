package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Country
import org.bitbucket.yujiorama.sakilaapp.model.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CountryController(
        @Autowired private val repository: CountryRepository
) {

    @GetMapping("/countries/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Country> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/countries")
    fun readAll(): List<Country> = repository.findAllByOrderByCountryAsc()

    @PostMapping("/countries")
    fun create(@RequestBody aCountry: Country): Country = repository.save(aCountry)

    @PutMapping("/countries/{id}")
    fun update(@RequestBody aCountry: Country, @PathVariable id: Number): ResponseEntity<Country> {

        return repository.findById(id.toInt()).map {
            val newCountryEntity = aCountry
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCountryEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/countries/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
