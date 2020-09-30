package org.bitbucket.yujiorama.sakilaapp.endpoint

import org.bitbucket.yujiorama.sakilaapp.model.Country
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CountryRepositoryTest(
        @Autowired
        val restTemplate: TestRestTemplate
) {

    @BeforeAll
    fun setup() {
        println(">> setup")
    }

    @AfterAll
    fun teardown() {
        println(">> teardown")
    }

    @Test
    fun `test for test`() {
        val entities = restTemplate.getForEntity<Country>("/countries")
        assert(entities.statusCode.is2xxSuccessful)
    }
}