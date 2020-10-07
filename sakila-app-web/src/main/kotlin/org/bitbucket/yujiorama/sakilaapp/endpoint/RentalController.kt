package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Rental
import org.bitbucket.yujiorama.sakilaapp.model.RentalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class RentalController(
        @Autowired private val repository: RentalRepository
) {

    @GetMapping("/rentals/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Rental> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/rentals")
    fun readAll(): List<Rental> = repository.findAllByOrderByIdAsc()

    @PostMapping("/rentals")
    fun create(@RequestBody aRental: Rental): Rental = repository.save(aRental)

    @PutMapping("/rentals/{id}")
    fun update(@RequestBody aRental: Rental, @PathVariable id: Number): ResponseEntity<Rental> {

        return repository.findById(id.toInt()).map {
            val newRental = aRental.copy(id = id.toInt(), lastUpdate = LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newRental))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/rentals/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
