package org.bitbucket.yujiorama.sakilaapp.batch.payment

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = batchBaseName)
data class PaymentProperties(
    val workdir: String = "",
    val output: String = "",
    val jobName: String = "$batchBaseName.Job",
    val stepName: String = "$batchBaseName.JobStep",
    val readerName: String = "$batchBaseName.JobReader",
    val writerName: String = "$batchBaseName.JobWriter",
    val chunkSize: Int = 10,
    val pageSize: Int = 100,
    val saveState: Boolean = true,
)
