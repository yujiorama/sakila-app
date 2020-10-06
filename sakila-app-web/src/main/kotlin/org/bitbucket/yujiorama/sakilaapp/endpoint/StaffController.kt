package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Staff
import org.bitbucket.yujiorama.sakilaapp.model.StaffRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class StaffController(
        @Autowired private val repository: StaffRepository
) {

    @GetMapping("/entities/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Staff> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/entities")
    fun readAll(): List<Staff> = repository.findAllByOrderByFirstNameAscLastNameAsc()

    @PostMapping("/entities")
    fun create(@RequestBody aStaff: Staff): Staff = repository.save(aStaff)

    @PutMapping("/entities/{id}")
    fun update(@RequestBody aStaff: Staff, @PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            val newStaff = aStaff.copy(id = id.toInt(), lastUpdate = LocalDateTime.now())
            repository.save(newStaff)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/entities/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
