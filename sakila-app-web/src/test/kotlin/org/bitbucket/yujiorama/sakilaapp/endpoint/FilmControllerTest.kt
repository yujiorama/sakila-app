package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Film
import org.bitbucket.yujiorama.sakilaapp.model.FilmRepository
import org.bitbucket.yujiorama.sakilaapp.model.Language
import org.bitbucket.yujiorama.sakilaapp.model.Rating
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
        @Autowired private val repository: FilmRepository
) {

    @Test
    fun `all request`() {
        given(repository.findAllByOrderByTitleAsc())
                .willReturn(listOf(
                        Film(id = 111, title = "aaa", description = "aaa", language = Language(id = 111, name = "ja"), rating = Rating.G, length = 120),
                        Film(id = 222, title = "bbb", description = "bbb", language = Language(id = 222, name = "en"), rating = Rating.NC_17, length = 120),
                        Film(id = 333, title = "ccc", description = "ccc", language = Language(id = 333, name = "fr"), rating = Rating.PG, length = 120),
                        Film(id = 444, title = "ddd", description = "ddd", language = Language(id = 444, name = "it"), rating = Rating.PG_13, length = 120),
                        Film(id = 555, title = "eee", description = "eee", language = Language(id = 555, name = "ru"), rating = Rating.R, length = 120)
                ))

        mockMvc.perform(get("/films")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].id").value(111))
                .andExpect(jsonPath("$[1].id").value(222))
                .andExpect(jsonPath("$[2].id").value(333))
                .andExpect(jsonPath("$[3].id").value(444))
                .andExpect(jsonPath("$[4].id").value(555))
    }

    @Test
    fun `read request`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Film(id = 1374, title = "aaa", description = "aaa", language = Language(id = 111, name = "ja"), rating = Rating.G, length = 120)))

        mockMvc.perform(get("/films/1374")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(1374))
                .andExpect(jsonPath("$.title").value("aaa"))
                .andExpect(jsonPath("$.language.name").value("ja"))
                .andExpect(jsonPath("$.rating").value("G"))
    }

    @Test
    fun `create request`() {
        given(repository.save(any(Film::class.java)))
                .willReturn(Film(id = 1374, title = "aaa", description = "aaa", language = Language(id = 111, name = "ja"), rating = Rating.PG_13, length = 120))

        mockMvc.perform(post("/films")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id":1374,
                        "title:"aaa",
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
                .andExpect(jsonPath("$.rating").value("PG-13"))
    }

    @Test
    fun `update request`() {
        given(repository.findById(1374))
                .willReturn(Optional.of(Film(id = 1374, title = "aaa", description = "aaa", language = Language(id = 111, name = "ja"), rating = Rating.G, length = 120)))
        given(repository.save(any(Film::class.java)))
                .willReturn(Film(id = 1374, title = "aaa", description = "aaa", language = Language(id = 111, name = "ja"), rating = Rating.PG_13, length = 120))

        mockMvc.perform(put("/films/1374")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "id":1374,
                        "title:"aaa",
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
                .andExpect(jsonPath("$.id").value(1374))
                .andExpect(jsonPath("$.rating").value("PG-13"))
    }
}