package org.bitbucket.yujiorama.sakilaapp

import com.intuit.karate.Runner
import com.intuit.karate.junit5.Karate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ExternalTest {

    @Karate.Test
    fun `sample`(): Karate = Karate.run("classpath:sample.feature")

    @Test
    fun `tags`() {

        val tags = Optional.ofNullable(System.getProperty("tags")).orElse("~@ignore")
        val result = Runner
            .path("classpath:sakila")
            .tags(tags.split(","))
            .parallel(10)
        Assertions.assertEquals(0, result.failCount, result.errorMessages)
    }

    @Test
    fun `all`() {

        val result = Runner
            .path("classpath:sakila")
            .parallel(10)
        Assertions.assertEquals(0, result.failCount, result.errorMessages)
    }
}
