package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.CityEntity
import org.bitbucket.yujiorama.sakilaapp.model.CityEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CityController(
        @Autowired private val repository: CityEntityRepository
) {

    @GetMapping("/cities/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<CityEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/cities")
    fun readAll(): List<CityEntity> = repository.findAllByOrderByCityAsc()

    @PostMapping("/cities")
    fun create(@RequestBody aCityEntity: CityEntity): CityEntity = repository.save(aCityEntity)

    @PutMapping("/cities/{id}")
    fun update(@RequestBody aCityEntity: CityEntity, @PathVariable id: Number): ResponseEntity<CityEntity> {

        return repository.findById(id.toInt()).map {
            val newCityEntity = aCityEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newCityEntity))
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
