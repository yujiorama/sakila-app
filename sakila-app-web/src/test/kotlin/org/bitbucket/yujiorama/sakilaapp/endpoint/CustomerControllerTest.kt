package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
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
class CustomerControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val repository: CustomerRepository,
    @Autowired private val storeRepository: StoreRepository,
) {

    private var fixture: Customer? = null

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

        fixture = Customer()
            .withId(1374)
            .withFirstName("First")
            .withLastName("Last")
            .withAddress(address)
            .withStore(store)
            .withLastUpdate(lastUpdate)
    }

    @Test
    fun `read request`() {
        val expected = fixture!!
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        mockMvc.perform(get("/customers/${expected.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.customer_id").value(expected.id!!))
    }

    @Test
    fun `update request`() {
        val expected = fixture!!
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))
        given(repository.save(any(Customer::class.java)))
            .willReturn(expected.withFirstName("LINDA").withLastName("WILLIAMS"))
        given(storeRepository.findById(expected.store.id!!))
            .willReturn(Optional.of(expected.store))

        mockMvc.perform(put("/customers/${expected.id}")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "customer_id": 1374,
                  "last_update": "2006-02-15T04:57:20",
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
                  "address": {
                    "address_id": 1374,
                    "last_update": "2006-02-15T04:45:30",
                    "address": "692 Joliet Street",
                    "district": " ",
                    "city": {
                      "city_id": 1374,
                      "last_update": "2006-02-15T04:45:25",
                      "city": "Athenai",
                      "country": {
                        "country_id": 1374,
                        "last_update": "2006-02-15T04:44:00",
                        "country": "Greece"
                      }
                    },
                    "phone": " ",
                    "address2": null,
                    "postal_code": "83579"
                  },
                  "first_name": "LINDA",
                  "last_name": "WILLIAMS",
                  "activebool": true,
                  "create_date": "2006-02-14T00:00:00",
                  "email": "LINDA.WILLIAMS@sakilacustomer.org",
                  "active": 1
                }
            """.trimIndent()))
            .andExpect(status().is2xxSuccessful)
    }
}