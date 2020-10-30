package org.bitbucket.yujiorama.sakilaapp.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import org.bitbucket.yujiorama.sakilaapp.model.Film
import org.bitbucket.yujiorama.sakilaapp.model.FilmRepository
import org.bitbucket.yujiorama.sakilaapp.model.Language
import org.bitbucket.yujiorama.sakilaapp.model.Rating
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@ContextConfiguration(classes = [ControllerTestConfig::class])
@WebMvcTest
class FilmControllerTest(
        @Autowired private val mockMvc: MockMvc,
        @Autowired private val repository: FilmRepository,
        @Autowired private val objectMapper: ObjectMapper
) {

    private val fixture = mapOf(
        111 to Film().withId(111).withTitle("aaa").withDescription("aaa").withLanguage(Language().withId(111).withName("ja")).withRating(Rating.G).withLength(120),
        222 to Film().withId(222).withTitle("bbb").withDescription("bbb").withLanguage(Language().withId(222).withName("en")).withRating(Rating.NC_17).withLength(120),
        333 to Film().withId(333).withTitle("ccc").withDescription("ccc").withLanguage(Language().withId(333).withName("fr")).withRating(Rating.PG).withLength(120),
        444 to Film().withId(444).withTitle("ddd").withDescription("ddd").withLanguage(Language().withId(444).withName("it")).withRating(Rating.PG_13).withLength(120),
        555 to Film().withId(555).withTitle("eee").withDescription("eee").withLanguage(Language().withId(555).withName("ru")).withRating(Rating.R).withLength(120),
        1374 to Film().withId(1374).withTitle("abcde").withDescription("abcde").withLanguage(Language().withId(1374).withName("ja")).withRating(Rating.G).withLength(120),
    )

    @Test
    fun `all request`() {
        given(repository.findAllByOrderByTitleAsc())
            .willReturn(ArrayList(fixture.values).sortedBy { it.id }.take(5))

        mockMvc.perform(get("/films")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].film_id").value(111))
            .andExpect(jsonPath("$[1].film_id").value(222))
            .andExpect(jsonPath("$[2].film_id").value(333))
            .andExpect(jsonPath("$[3].film_id").value(444))
            .andExpect(jsonPath("$[4].film_id").value(555))
    }

    @Test
    fun `read request`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))

        mockMvc.perform(get("/films/1374")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.film_id").value(expected.id!!))
            .andExpect(jsonPath("$.title").value(expected.title))
            .andExpect(jsonPath("$.language.name").value(expected.language.name))
            .andExpect(jsonPath("$.rating").value(expected.rating.label))
    }

    @Test
    fun `enum class serialize test`() {
        val actual = objectMapper.writeValueAsString(Rating.NC_17)
        Assertions.assertEquals("\"NC-17\"", actual)
    }

    @Test
    fun `enum class deserialize test`() {
        val actual = objectMapper.readValue<Rating>("\"PG-13\"", Rating::class.java)
        Assertions.assertEquals(Rating.PG_13, actual)
    }

    @Test
    fun `create request`() {
        val requestFilm = fixture.getValue(111)
        val savedFilm = requestFilm.withId(1374).withTitle("saved")

        given(repository.save(any(Film::class.java)))
            .willReturn(savedFilm)

        mockMvc.perform(post("/films")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestFilm)))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.title").value(savedFilm.title))
            .andExpect(jsonPath("$.rating").value(savedFilm.rating.label))
    }

    @Test
    fun `update request`() {
        val expected = fixture.getValue(1374)
        given(repository.findById(expected.id!!))
            .willReturn(Optional.of(expected))
        given(repository.save(any(Film::class.java)))
            .willReturn(expected.withRating(Rating.PG_13))

        mockMvc.perform(put("/films/1374")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "id":1374,
                        "title":"aaa",
                        "description":"aaa",
                        "language": {
                            "id":111,
                            "name":"ja"
                        },
                        "rating": "PG-13",
                        "length": 120
                    }
                """.trimIndent().trimMargin()))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.film_id").value(expected.id!!))
            .andExpect(jsonPath("$.rating").value("PG-13"))
    }
}