package org.bitbucket.yujiorama.sakilaapp

import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.batch.core.listener.StepExecutionListenerSupport
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
@ConfigurationPropertiesScan
class BatchConfig {

    @Bean
    fun jobLogger(): JobExecutionListener {

        val logger = LoggerFactory.getLogger(BatchConfig::class.java.packageName + ".JobLogger")

        return object : JobExecutionListenerSupport() {

            override fun beforeJob(jobExecution: JobExecution) {

                logger.info(
                    "start: name=[{}], start_at=[{}]",
                    jobExecution.jobConfigurationName,
                    jobExecution.startTime,
                )
            }

            override fun afterJob(jobExecution: JobExecution) {

                logger.info(
                    "finish: name=[{}], end_at=[{}], batch_status=[{}], exit_status=[{}]",
                    jobExecution.jobConfigurationName,
                    jobExecution.endTime,
                    jobExecution.status,
                    jobExecution.exitStatus,
                )
            }
        }
    }

    @Bean
    fun stepLogger(): StepExecutionListener {

        val logger = LoggerFactory.getLogger(BatchConfig::class.java.packageName + ".StepLogger")

        return object : StepExecutionListenerSupport() {

            override fun beforeStep(stepExecution: StepExecution) {

                logger.info(
                    "start: name=[{}], start_at=[{}]",
                    stepExecution.stepName,
                    stepExecution.startTime,
                )
            }

            override fun afterStep(stepExecution: StepExecution): ExitStatus? {

                logger.info(
                    "finish: name=[{}], end_at=[{}], batch_status=[{}], exit_status=[{}]",
                    stepExecution.stepName,
                    stepExecution.endTime,
                    stepExecution.status,
                    stepExecution.exitStatus,
                )

                return ExitStatus.COMPLETED
            }
        }
    }
}
