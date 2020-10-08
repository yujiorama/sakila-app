package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.StaffEntity
import org.bitbucket.yujiorama.sakilaapp.model.StaffEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class StaffController(
        @Autowired private val repository: StaffEntityRepository
) {

    @GetMapping("/staffs/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<StaffEntity> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/staffs")
    fun readAll(): List<StaffEntity> = repository.findAllByOrderByFirstNameAscLastNameAsc()

    @PostMapping("/staffs")
    fun create(@RequestBody aStaffEntity: StaffEntity): StaffEntity = repository.save(aStaffEntity)

    @PutMapping("/staffs/{id}")
    fun update(@RequestBody aStaffEntity: StaffEntity, @PathVariable id: Number): ResponseEntity<StaffEntity> {

        return repository.findById(id.toInt()).map {
            val newStaffEntity = aStaffEntity
                    .withId(id.toInt())
                    .withLastUpdate(LocalDateTime.now())
            ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newStaffEntity))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/staffs/{id}")
    fun delete(@PathVariable id: Number): ResponseEntity<Void> {

        return repository.findById(id.toInt()).map {
            repository.delete(it)
            ResponseEntity.noContent().build<Void>()
        }.orElse(ResponseEntity.notFound().build())
    }
}
