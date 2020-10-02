package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Country
import org.bitbucket.yujiorama.sakilaapp.model.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CountryController(
        @Autowired private val countryRepository: CountryRepository
) {

    @GetMapping("/countries/{id}")
    fun get(@PathVariable id: Number): ResponseEntity<Country> {

        return countryRepository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/countries")
    fun findAll(): List<Country> = countryRepository.findAll().iterator().asSequence().toList()
}
