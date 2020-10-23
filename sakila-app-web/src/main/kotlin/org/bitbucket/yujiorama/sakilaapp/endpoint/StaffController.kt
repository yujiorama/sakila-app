package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Staff
import org.bitbucket.yujiorama.sakilaapp.model.StaffRepository
import org.bitbucket.yujiorama.sakilaapp.model.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class StaffController(
        @Autowired private val repository: StaffRepository,
        @Autowired private val storeRepository: StoreRepository
) {

    @GetMapping("/staffs/{id}")
    fun read(@PathVariable id: Number): ResponseEntity<Staff> {

        return repository.findById(id.toInt()).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/staffs")
    fun readAll(): List<Staff> = repository.findAllByOrderByFirstNameAscLastNameAsc()

    @PostMapping("/staffs")
    fun create(@RequestBody aStaff: Staff): Staff = repository.save(aStaff)

    @PutMapping("/staffs/{id}")
    fun update(@RequestBody aStaff: Staff, @PathVariable id: Number): ResponseEntity<Staff> {

        return repository.findById(id.toInt()).map {
            val newStaffEntity = aStaff
                .withId(id.toInt())
                .withLastUpdate(LocalDateTime.now())
                .withStore(storeRepository.findById(aStaff.store.id!!).get())
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
