package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.City
import org.bitbucket.yujiorama.sakilaapp.model.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CityController(
        @Autowired private val repository: CityRepository
) {

    @GetMapping("/cities/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<City> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/cities")
    fun readAll(): List<City> = repository.findAllByOrderByCityAsc()

    @PostMapping("/cities")
    fun create(@RequestBody aCity: City): City = repository.save(aCity)

    @PutMapping("/cities/{id}")
    fun update(@RequestBody aCity: City, @PathVariable id: Number): ResponseEntity<City> {

        return repository.findById(id.toInt()).map {
            val newCity = aCity.copy(id = id.toInt(), lastUpdate = LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/cities/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
