package org.bitbucket.yujiorama.sakilaapp.batch.payment

import org.bitbucket.yujiorama.sakilaapp.model.Payment
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.listener.ItemListenerSupport
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import javax.persistence.EntityManagerFactory

const val batchBaseName = "org.bitbucket.yujiorama.sakilaapp.batch.payment"

@Configuration("$batchBaseName.config")
class Config(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val props: PaymentProperties,
) {

    @Bean(name = ["$batchBaseName.reader"])
    fun reader(entityManagerFactory: EntityManagerFactory): ItemReader<Payment> =
        JpaPagingItemReaderBuilder<Payment>()
            .name(props.readerName)
            .saveState(props.saveState)
            .pageSize(props.pageSize)
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT p from org.bitbucket.yujiorama.sakilaapp.model.Payment p ORDER BY p.id ASC")
            .maxItemCount(200)
            .build()

    @Bean(name = ["$batchBaseName.processor"])
    fun processor(): PaymentProcessor = PaymentProcessor()

    @Bean(name = ["$batchBaseName.writer"])
    fun writer(): ItemWriter<PaymentDTO> =
        FlatFileItemWriterBuilder<PaymentDTO>()
            .name(props.writerName)
            .saveState(props.saveState)
            .encoding(StandardCharsets.UTF_8.displayName())
            .append(false)
            .resource(
                FileSystemResource(Paths.get(props.workdir, props.output))
            )
            .lineAggregator(
                DelimitedLineAggregator<PaymentDTO>().let {
                    val fieldExtractor = BeanWrapperFieldExtractor<PaymentDTO>()

                    fieldExtractor.setNames(PaymentDTO.fields())
                    fieldExtractor.afterPropertiesSet()

                    it.setDelimiter(",")
                    it.setFieldExtractor(fieldExtractor)
                    it
                }
            )
            .headerCallback {
                it.write(PaymentDTO.fields().joinToString())
            }
            .build()

    @Bean(name = ["$batchBaseName.itemLogger"])
    fun itemLogger(): ItemListenerSupport<Payment, PaymentDTO> {

        val logger = LoggerFactory.getLogger(Config::class.java.packageName + ".ItemLogger")

        return object : ItemListenerSupport<Payment, PaymentDTO>() {
            override fun afterRead(item: Payment) {

                logger.info("afterRead item: {}", item.toString())
            }

            override fun afterWrite(items: MutableList<out PaymentDTO>) {

                items.map { logger.info("afterWrite item: {}", it.toString()) }
            }
        }
    }

    fun jobLogger(): JobExecutionListenerSupport = object : JobExecutionListenerSupport() {
        override fun afterJob(jobExecution: JobExecution) {

            val logger = LoggerFactory.getLogger(Config::class.java.packageName + ".JobLogger")

            logger.info("output: {}", Paths.get(props.workdir, props.output))
        }
    }


    @Bean(name = ["$batchBaseName.job"])
    fun job(
        jobListener: JobExecutionListener,
        stepListener: StepExecutionListener,
        reader: ItemReader<Payment>,
    ): Job =
        jobBuilderFactory
            .get(props.jobName)
            .incrementer(RunIdIncrementer())
            .listener(jobListener)
            .listener(jobLogger())
            .preventRestart()
            .flow(
                stepBuilderFactory
                    .get(props.stepName)
                    .chunk<Payment, PaymentDTO>(props.chunkSize)
                    .reader(reader)
                    .processor(processor())
                    .writer(writer())
                    .listener(stepListener)
                    .listener(itemLogger())
                    .build()
            )
            .end()
            .build()
}
