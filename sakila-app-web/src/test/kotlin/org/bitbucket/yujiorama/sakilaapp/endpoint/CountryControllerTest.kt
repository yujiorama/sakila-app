package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Country
import org.bitbucket.yujiorama.sakilaapp.model.CountryRepository
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*

@ContextConfiguration(classes = [ControllerTestConfig::class])
@WebMvcTest
class CountryControllerTest(
        @Autowired private val mockMvc: MockMvc,
        @Autowired private val repository: CountryRepository
) {

    @Test
    fun `all request`() {
        given(repository.findAllByOrderByCountryAsc())
                .willReturn(listOf(
                        Country(111, "aaa"),
                        Country(222, "bbb"),
                        Country(333, "ccc")
                ))

        mockMvc.perform(get("/countries")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].id").value(111))
                .andExpect(jsonPath("$[1].id").value(222))
                .andExpect(jsonPath("$[2].id").value(333))
    }

    @Test
    fun `read request`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Country(1374, "aaa")))

        mockMvc.perform(get("/countries/1374")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(1374))
                .andExpect(jsonPath("$.country").value("aaa"))
    }

    @Test
    fun `read request(missing)`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Country(1374, "aaa")))

        mockMvc.perform(get("/countries/1111")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `update request`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Country(1374, "aaa")))
        val lastUpdate = LocalDateTime.of(2020, 1, 2, 3, 45, 6)
        given(repository.save(any(Country::class.java)))
                .willReturn(Country(1374, "bbb", lastUpdate))

        mockMvc.perform(put("/countries/1374")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"country\":\"bbb\", \"lastUpdate\":\"2020-01-02T03:45:06+09:00\"}"))
                .andExpect(status().is2xxSuccessful)
    }


    @Test
    fun `update request(missing)`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Country(1374, "aaa")))
        val lastUpdate = LocalDateTime.of(2020, 1, 2, 3, 45, 6)
        given(repository.save(any(Country::class.java)))
                .willReturn(Country(1374, "bbb", lastUpdate))

        mockMvc.perform(put("/countries/1111")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"country\":\"bbb\", \"lastUpdate\":\"2020-01-02T03:45:06+09:00\"}"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `delete request`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Country(1374, "aaa")))

        mockMvc.perform(delete("/countries/1374")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `delete request(missing)`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Country(1374, "aaa")))

        mockMvc.perform(get("/countries/1111")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }
}
