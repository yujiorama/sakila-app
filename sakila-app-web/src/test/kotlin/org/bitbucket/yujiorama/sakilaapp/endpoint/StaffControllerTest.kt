package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*

@ContextConfiguration(classes = [ControllerTestConfig::class])
@WebMvcTest
class StaffControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val repository: StaffRepository,
    @Autowired private val storeRepository: StoreRepository,
) {

    private var fixture: Staff? = null

    @BeforeEach
    fun setupFixture() {
        val lastUpdate = LocalDateTime.now()
        val address = Address()
            .withId(1374)
            .withAddress("aaa")
            .withCity(City()
                .withId(1374)
                .withCity("city")
                .withCountry(Country()
                    .withId(1374)
                    .withCountry("country")
                    .withLastUpdate(lastUpdate))
                .withLastUpdate(lastUpdate))
            .withLastUpdate(lastUpdate)

        var store = Store()
            .withId(1374)
            .withAddress(address)
            .withLastUpdate(lastUpdate)
        val staff = Staff()
            .withId(1374)
            .withFirstName("Mike")
            .withLastName("Hillyer")
            .withAddress(address)
            .withEmail("aaa@example.com")
            .withActive(true)
            .withUsername("Mike")
            .withPassword("8cb2237d0679ca88db6464eac60da96345513964")
            .withStore(store)
            .withLastUpdate(lastUpdate)
        store.managerStaff = staff

        fixture = staff
    }

    @Test
    fun `read request`() {
        val expected = fixture!!
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        mockMvc.perform(get("/staffs/${expected.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.staff_id").value(expected.id!!))
    }

    @Test
    fun `update request`() {
        val expected = fixture!!
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))
        given(repository.save(any(Staff::class.java)))
            .willReturn(expected.withFirstName("LINDA").withLastName("WILLIAMS"))
        given(storeRepository.findById(expected.store.id!!))
            .willReturn(Optional.of(expected.store))

        mockMvc.perform(put("/staffs/${expected.id}")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "staff_id": 1374,
                  "last_update": "2006-02-15T04:57:16",
                  "first_name": "Mike",
                  "last_name": "Hillyer",
                  "address": {
                    "address_id": 1374,
                    "last_update": "2020-10-28T04:28:45.219798",
                    "address": "ころころ",
                    "district": "ひそひそ",
                    "city": {
                      "city_id": 1374,
                      "last_update": "2006-02-15T04:45:25",
                      "city": "Lethbridge",
                      "country": {
                        "country_id": 1374,
                        "last_update": "2006-02-15T04:44:00",
                        "country": "Canada"
                      }
                    },
                    "phone": " ",
                    "address2": null,
                    "postal_code": null
                  },
                  "email": "Mike.Hillyer@sakilastaff.com",
                  "store": {
                    "store_id": 1374,
                    "last_update": "2006-02-15T04:57:12",
                    "address": {
                      "address_id": 1374,
                      "last_update": "2006-02-15T04:45:30",
                      "address": "47 MySakila Drive",
                      "district": " ",
                      "city": {
                        "city_id": 1374,
                        "last_update": "2006-02-15T04:45:25",
                        "city": "Lethbridge",
                        "country": {
                          "country_id": 1374,
                          "last_update": "2006-02-15T04:44:00",
                          "country": "Canada"
                        }
                      },
                      "phone": " ",
                      "address2": null,
                      "postal_code": null
                    }
                  },
                  "active": true,
                  "username": "Mike",
                  "password": "8cb2237d0679ca88db6464eac60da96345513964",
                  "picture": null
                }
            """.trimIndent()))
            .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun `update request(not found)`() {
        val expected = fixture!!
        given(repository.findById(anyInt()))
            .willReturn(Optional.empty())
//        given(repository.save(any(Staff::class.java)))
//            .willReturn(expected.withFirstName("LINDA").withLastName("WILLIAMS"))
//        given(storeRepository.findById(expected.store.id!!))
//            .willReturn(Optional.of(expected.store))

        mockMvc.perform(put("/staffs/${expected.id}")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "staff_id": 1374,
                  "last_update": "2006-02-15T04:57:16",
                  "first_name": "Mike",
                  "last_name": "Hillyer",
                  "address": {
                    "address_id": 1374,
                    "last_update": "2020-10-28T04:28:45.219798",
                    "address": "ころころ",
                    "district": "ひそひそ",
                    "city": {
                      "city_id": 1374,
                      "last_update": "2006-02-15T04:45:25",
                      "city": "Lethbridge",
                      "country": {
                        "country_id": 1374,
                        "last_update": "2006-02-15T04:44:00",
                        "country": "Canada"
                      }
                    },
                    "phone": " ",
                    "address2": null,
                    "postal_code": null
                  },
                  "email": "Mike.Hillyer@sakilastaff.com",
                  "store": {
                    "store_id": 1374,
                    "last_update": "2006-02-15T04:57:12",
                    "address": {
                      "address_id": 1374,
                      "last_update": "2006-02-15T04:45:30",
                      "address": "47 MySakila Drive",
                      "district": " ",
                      "city": {
                        "city_id": 1374,
                        "last_update": "2006-02-15T04:45:25",
                        "city": "Lethbridge",
                        "country": {
                          "country_id": 1374,
                          "last_update": "2006-02-15T04:44:00",
                          "country": "Canada"
                        }
                      },
                      "phone": " ",
                      "address2": null,
                      "postal_code": null
                    }
                  },
                  "active": true,
                  "username": "Mike",
                  "password": "8cb2237d0679ca88db6464eac60da96345513964",
                  "picture": null
                }
            """.trimIndent()))
            .andExpect(status().isNotFound)
    }
}