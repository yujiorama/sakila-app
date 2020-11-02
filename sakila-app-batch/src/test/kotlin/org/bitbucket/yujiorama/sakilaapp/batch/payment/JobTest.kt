package org.bitbucket.yujiorama.sakilaapp.batch.payment

import org.bitbucket.yujiorama.sakilaapp.BatchConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBatchTest
@SpringJUnitConfig(
    classes = [
        BatchConfig::class,
        Config::class,
    ]
)
@TestPropertySource(
    locations = [
        "classpath:application.properties",
        "classpath:application-test.properties",
    ],
)
@EnableAutoConfiguration
@EnableTransactionManagement
class JobTest(
    @Autowired private val jobLauncherTestUtils: JobLauncherTestUtils,
) {

    @Test
    fun `bean creation`() {
        Assertions.assertNotNull(jobLauncherTestUtils)
    }

    @Disabled("The launchJob did not start job immediately.")
    @Test
    fun `job result`() {
        val execution = jobLauncherTestUtils.launchJob()
        Assertions.assertTrue(execution.isStopping)
        Assertions.assertEquals(BatchStatus.COMPLETED, execution.status)
        Assertions.assertEquals(ExitStatus.COMPLETED, execution.exitStatus)
    }
}
