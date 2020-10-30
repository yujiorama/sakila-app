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

    private val fixture: Map<Int, Country> = mapOf(
        111 to Country().withId(111).withCountry("aaa"),
        222 to Country().withId(222).withCountry("bbb"),
        333 to Country().withId(333).withCountry("ccc"),
        1374 to Country().withId(1374).withCountry("abc"),
    )

    @Test
    fun `all request`() {
        given(repository.findAllByOrderByCountryAsc())
            .willReturn(ArrayList(fixture.values).sortedBy { it.id }.take(3))

        mockMvc.perform(get("/countries")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].country_id").value(111))
            .andExpect(jsonPath("$[1].country_id").value(222))
            .andExpect(jsonPath("$[2].country_id").value(333))
    }

    @Test
    fun `read request`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        mockMvc.perform(get("/countries/1374")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.country_id").value(expected.id!!))
            .andExpect(jsonPath("$.country").value(expected.country))
    }

    @Test
    fun `read request(missing)`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        mockMvc.perform(get("/countries/1111")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `update request`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))
        val lastUpdate = LocalDateTime.of(2020, 1, 2, 3, 45, 6)
        given(repository.save(any(Country::class.java)))
            .willReturn(expected.withCountry("bbb").withLastUpdate(lastUpdate))

        mockMvc.perform(put("/countries/1374")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"country\":\"bbb\", \"last_update\":\"2020-01-02T03:45:06.789\"}"))
            .andExpect(status().is2xxSuccessful)
    }


    @Test
    fun `update request(missing)`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))
        val lastUpdate = LocalDateTime.of(2020, 1, 2, 3, 45, 6)
        given(repository.save(any(Country::class.java)))
            .willReturn(expected.withCountry("bbb").withLastUpdate(lastUpdate))

        mockMvc.perform(put("/countries/1111")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"country\":\"bbb\", \"last_update\":\"2020-01-02T03:45:06.789\"}"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `delete request`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        mockMvc.perform(delete("/countries/1374")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `delete request(missing)`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        mockMvc.perform(get("/countries/1111")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }
}
